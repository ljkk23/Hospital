package edu.swu.cs.securityService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.swu.cs.Constants.SystemConstants;
import edu.swu.cs.domain.securityEntity.Menu;
import edu.swu.cs.domain.securityEntity.MenuVo;
import edu.swu.cs.mapper.MenuMapper;

import edu.swu.cs.mapper.RoleMapper;
import edu.swu.cs.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujian
 * @since 2022-11-16
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<MenuVo> getRoutersByUserID(Long userID) {
        List<Menu> menus = null;
        if (userID==1){
            menus = menuMapper.selectAllRouters();
        }else {
            menus = menuMapper.selectRoutersByUserId(userID);
        }
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        List<MenuVo> parentMenus = menuVos.stream()
                .filter(menuVo -> menuVo.getParentId().equals(0L))
                .map(menuVo -> menuVo.setChildren(getRouterChildren(menuVo,menuVos)))
                .collect(Collectors.toList());
        return parentMenus;
    }

    public  List<MenuVo> getRouterChildren(MenuVo parentMenu,List<MenuVo> menuVoList){
        List<MenuVo> menuVos = menuVoList.stream()
                .filter(menuVo -> menuVo.getParentId().equals(parentMenu.getId()))
                .map(menuVo -> menuVo.setChildren(getRouterChildren(menuVo, menuVoList)))
                .collect(Collectors.toList());

        return menuVos;
    }

    @Override
    public List<String> getPermissions(Long userID) {
        //如果是超级管理员，就返回所有的权限
        if (userID==1){
            LambdaQueryWrapper<Menu> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            lambdaQueryWrapper.in(Menu::getMenuType,SystemConstants.MENU,SystemConstants.BUTTON);
            List<Menu> menus = menuMapper.selectList(lambdaQueryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        List<String> menuByUserId = menuMapper.selectPermsByUserId(userID);
        return menuByUserId;
    }

    @Override
    public List<String> getRoles(Long userID) {
        //如果是超级管理员，就返回admin
        if (userID==1){
            List<String> list=new ArrayList<>();
            list.add("admin");
            return list;
        }
        List<String> roleByUserId = roleMapper.getRoleByUserId(userID);
        return roleByUserId;
    }

}
