package edu.swu.cs.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author liujian
 * @since 2022-05-26
 */
@Getter
@Setter

public class LoginUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;




}
