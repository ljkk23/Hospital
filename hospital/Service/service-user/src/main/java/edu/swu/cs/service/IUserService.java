package edu.swu.cs.service;

import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.swu.cs.entity.model.AddUserModel;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
public interface IUserService extends IService<User> {

    ResponseResult addUser(AddUserModel addUserModel);

    ResponseResult updateUserInfo(User user);
}
