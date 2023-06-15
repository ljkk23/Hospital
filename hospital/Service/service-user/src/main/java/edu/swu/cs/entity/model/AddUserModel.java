package edu.swu.cs.entity.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddUserModel {
    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("手机号")
    private String phonenumber;
}
