package edu.swu.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.domain.securityEntity.UserDetailsImpl;
import edu.swu.cs.domain.securityEntity.UserVO;
import edu.swu.cs.entity.User;
import edu.swu.cs.entity.VO.UserRegisterVO;
import edu.swu.cs.entity.model.AddUserModel;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.mapper.UserMapper;
import edu.swu.cs.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.swu.cs.utils.BeanCopyUtils;
import edu.swu.cs.utils.JwtUtil;
import edu.swu.cs.utils.MD5Util;
import edu.swu.cs.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult addUser(AddUserModel addUserModel) {
        System.out.println(addUserModel.toString());
        String encode = MD5Util.encode(addUserModel.getPassword());
        addUserModel.setPassword(encode);
        //check是否已经注册过
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName,addUserModel.getUserName())
                .eq(User::getPhonenumber,addUserModel.getPhonenumber());
        if(!Objects.isNull(this.getOne(lambdaQueryWrapper))){
            return ResponseResult.okResult(AppHttpCodeEnum.USERNAME_EXIST);
        }
        //保存到数据库
        User user = BeanCopyUtils.copyBean(addUserModel, User.class);
        if (!this.save(user)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"插入数据库出错");
        }
        System.out.println(user.toString());
        User one = this.getOne(lambdaQueryWrapper);
        Long userID=one.getId();
        //自动登陆
        String jwt= JwtUtil.createJWTForApp("user-"+userID);
        String userIDVO="user-"+userID;

        UserVO userVO = BeanCopyUtils.copyBean(one, UserVO.class);
        //存入redis
        redisCache.setCacheObject("Login:"+userIDVO,new UserDetailsImpl(userVO));
        UserRegisterVO userLoginVO=new UserRegisterVO(jwt,one.getId(), one.getUserName());
        System.out.println(userLoginVO.toString());
        return ResponseResult.okResult(userLoginVO);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {

        LambdaUpdateWrapper<User> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getUserName,user.getUserName())
                .set(user.getPassword()!=null && !user.getPassword().trim().isEmpty(),User::getPassword,MD5Util.encode(user.getPassword()))
                .set(user.getPhonenumber()!=null && !user.getPhonenumber().trim().isEmpty(),User::getPhonenumber,user.getPhonenumber())
                .set(user.getCardId()!=null && !user.getCardId().trim().isEmpty(),User::getCardId,user.getCardId())
                .set(user.getSex()!=null && user.getSex().trim().isEmpty(),User::getSex,user.getSex());
        if(!this.update(lambdaUpdateWrapper)){
            return ResponseResult.errorResult(500,"没有该用户");
        }
        return ResponseResult.okResult();
    }
}
