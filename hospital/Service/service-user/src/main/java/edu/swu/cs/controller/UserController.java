package edu.swu.cs.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Doctor;
import edu.swu.cs.entity.Patient;
import edu.swu.cs.entity.User;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @PostMapping("/addUser")
    public ResponseResult addUser(User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if (!userService.save(user)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"插入数据库出错");
        }
        return ResponseResult.okResult();
    }

    @GetMapping("/getUserByFeign")
    public User getUserByFeign(String userName){
        LambdaQueryWrapper<User> LambdaQueryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper.eq(User::getUserName,userName);
        return userService.getOne(LambdaQueryWrapper);
    }
}

