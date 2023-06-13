package edu.swu.cs.controller;

import edu.swu.cs.Exception.SystemException;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.LoginUserDTO;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginController {
    @Autowired
    private LoginService loginService;

        @PostMapping("/login")
        public ResponseResult login(@RequestBody LoginUserDTO user){
            if (!StringUtils.hasText(user.getUserName())){
                throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
            }
            return loginService.login(user);
        }
//    @PostMapping("/logout")
//    public ResponseResult logout(){
//        return LoginService.logout();
//    }
}