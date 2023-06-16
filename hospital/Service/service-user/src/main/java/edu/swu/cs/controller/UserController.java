package edu.swu.cs.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.swu.cs.annotation.systemLog;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Doctor;
import edu.swu.cs.entity.Patient;
import edu.swu.cs.entity.User;
import edu.swu.cs.entity.model.AddUserModel;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.service.IUserService;
import edu.swu.cs.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 用户注册账户
     * @param addUserModel
     * @return
     */
    @PostMapping("/addUser")
    public ResponseResult addUser(@RequestBody AddUserModel addUserModel){
        return userService.addUser(addUserModel);
    }

    @systemLog(businessName = "更改用户信息")
    @PostMapping("/updateUserInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){

        return userService.updateUserInfo(user);
    }

    @GetMapping("/getUserByFeign")
    public User getUserByFeign(String userName){
        LambdaQueryWrapper<User> LambdaQueryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper.eq(User::getUserName,userName);
        return userService.getOne(LambdaQueryWrapper);
    }
}

