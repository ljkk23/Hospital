package org.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.entity.UserInfo;
import org.example.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liujian
 * @since 2023-10-27
 */
@RestController
@RequestMapping("/user-info")
public class UserInfoController {

    @Autowired
    IUserInfoService userInfoService;
    @GetMapping ("/insertUser")
    public boolean insertUser(String name){
        UserInfo userInfo=new UserInfo(name);
        //userInfo.setMtime(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        userInfo.setMtime(LocalDateTime.now());
        return userInfoService.save(userInfo);
    }

    @GetMapping ("/updateUser")
    public boolean updateUser(String name){
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getName,name);
        UserInfo one = userInfoService.getOne(lambdaQueryWrapper);
        System.out.println(one.getMtime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        UserInfo userInfo=new UserInfo();
        userInfo.setCtime(LocalDateTime.now());
        userInfo.setName(one.getName());
        userInfo.setId(one.getId());
        return userInfoService.updateById(userInfo);
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_WEEK_DATE));
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        HashMap<String, String> alist = new HashMap<String, String>();


    }



}

