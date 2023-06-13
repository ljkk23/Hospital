package edu.swu.cs.controller;


import edu.swu.cs.Exception.SystemException;
import edu.swu.cs.domain.ResponseResult;

import edu.swu.cs.domain.securityEntity.MenuVo;
import edu.swu.cs.entity.VO.UserPermissionInfoVO;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.securityService.IMenuService;
import edu.swu.cs.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liujian
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @GetMapping("/getRouters")
    public ResponseResult getRouters(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String userID;
        try {
            userID= String.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        String[] userIdArray =  userID.split("-");
        if (!userID.contains("doctor-")){
            throw new SystemException(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        String realUserId=userIdArray[1];
        List<MenuVo> menuVos=menuService.getRoutersByUserID(Long.valueOf(realUserId));
        return ResponseResult.okResult(menuVos);
    }

    @GetMapping("/getPermissionInfo")
    public ResponseResult getPermissionInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        String userID;
        try {
            userID= String.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        String[] userIdArray =  userID.split("-");
        if (!userID.contains("doctor-")){
            throw new SystemException(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        Long realUserId=Long.valueOf(userIdArray[1]);
        List<String> permissions = menuService.getPermissions(realUserId);
        List<String> roles = menuService.getRoles(realUserId);


        return ResponseResult.okResult(new UserPermissionInfoVO(permissions,roles));
    }
}

