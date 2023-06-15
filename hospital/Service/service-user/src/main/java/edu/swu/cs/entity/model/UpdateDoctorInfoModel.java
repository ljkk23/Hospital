package edu.swu.cs.entity.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateDoctorInfoModel {
    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("医生职称")
    private String title;

    @ApiModelProperty("用户名")
    private String userName;


    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("如果是医生就是挂号费")
    private Integer amount;
}
