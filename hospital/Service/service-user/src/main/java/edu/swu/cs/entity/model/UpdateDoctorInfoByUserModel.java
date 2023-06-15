package edu.swu.cs.entity.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateDoctorInfoByUserModel {
    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phonenumber;


    @ApiModelProperty("头像地址")
    private String avatar;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("医生的简介")
    private String introduce;
}
