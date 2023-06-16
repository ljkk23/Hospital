package edu.swu.cs.entity.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ModifyPatientInfoModel {

    private Long id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("身份证号码")
    private String cardId;

    @ApiModelProperty("手机号")
    private String phonenumber;

    @ApiModelProperty("用户性别（0男，1女，2未知）")
    private String sex;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("过往病史")
    private String medicalHistory;



    private String  uuid;
}
