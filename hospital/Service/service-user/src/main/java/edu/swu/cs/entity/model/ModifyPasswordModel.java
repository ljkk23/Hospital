package edu.swu.cs.entity.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ModifyPasswordModel {
    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("密码")
    private String password;
}
