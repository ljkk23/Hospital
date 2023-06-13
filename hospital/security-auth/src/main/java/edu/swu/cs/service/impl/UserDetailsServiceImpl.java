package edu.swu.cs.service.impl;

import edu.swu.cs.client.UserClient;
import edu.swu.cs.domain.FeignVO.DoctorFeignVO;
import edu.swu.cs.domain.FeignVO.UserFeignVO;
import edu.swu.cs.domain.securityEntity.UserDetailsImpl;
import edu.swu.cs.domain.securityEntity.UserVO;
import edu.swu.cs.mapper.MenuMapper;
import edu.swu.cs.securityService.IMenuService;
import edu.swu.cs.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private IMenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO userVO=new UserVO();
        //根据username前缀的不同来分别进行远程调用，就是查表
        if (username.contains("doctor-")){
            String[] userNameArray =  username.split("-");
            String realUserName=userNameArray[1];
            DoctorFeignVO doctorByFeign = userClient.getDoctorByFeign(realUserName);
            if (Objects.isNull(doctorByFeign)){
                throw new RuntimeException("用户不存在");
            }
            userVO = BeanCopyUtils.copyBean(doctorByFeign, UserVO.class);
        } else if (username.contains("user-")) {
            String[] userNameArray =  username.split("-");
            String realUserName=userNameArray[1];
            UserFeignVO userByFeign = userClient.getUserByFeign(realUserName);
            if (Objects.isNull(userByFeign)){
                throw new RuntimeException("用户不存在");
            }
            userVO = BeanCopyUtils.copyBean(userByFeign, UserVO.class);
        }
        //查询该用户的权限
        List<String> permissions = menuService.getPermissions(userVO.getId());

        return new UserDetailsImpl(userVO,permissions);

    }
}
