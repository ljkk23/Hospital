package edu.swu.cs.service.impl;

import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.domain.securityEntity.UserDetailsImpl;
import edu.swu.cs.entity.LoginUserDTO;
import edu.swu.cs.entity.UserLoginVO;
import edu.swu.cs.service.LoginService;
import edu.swu.cs.utils.JwtUtil;
import edu.swu.cs.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

//    @Override
//    public ResponseResult logout() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
//        Long userID = userDetails.getUser().getId();
//        redisCache.deleteObject("blogLogin:"+userID);
//        return ResponseResult.okResult();
//    }

    @Override
    public ResponseResult login(LoginUserDTO user) {
        //通过UsernamePasswordAuthenticationToken密码检验来authenticate
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误2222");
        }
        UserDetailsImpl userDetailsimpl = (UserDetailsImpl) authenticate.getPrincipal();
        String userID=userDetailsimpl.getUser().getId().toString();
        String jwt=null;
        String userIDVO=null;
        //user的前缀不同，来封装userVO
        if (user.getUserName().contains("doctor-")){
            jwt= JwtUtil.createJWT("doctor-"+userID);
            userIDVO="doctor-"+userID;
        } else if (user.getUserName().contains("user-")) {
            jwt= JwtUtil.createJWT("user-"+userID);
            userIDVO="user-"+userID;
        }
        //存入redis
        redisCache.setCacheObject("Login:"+userIDVO,userDetailsimpl);
        UserLoginVO userLoginVO=new UserLoginVO(jwt,userDetailsimpl.getUser());
        return ResponseResult.okResult(userLoginVO);
    }
}