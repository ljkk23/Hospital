package edu.swu.cs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 医生的请假表
 * </p>
 *
 * @author liujian
 * @since 2023-06-21
 */
@Getter
@Setter
@TableName("doctor_leave")
@ApiModel(value = "DoctorLeave对象", description = "医生的请假表")
public class  DoctorLeave implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("请假的用户名")
    private String userName;

    @ApiModelProperty("真实名字")
    private String realName;

    private String deptName;

    @ApiModelProperty("请假类型，")
    private String type;

    private Long startTime;

    private Long endTime;

    private String detail;

    @ApiModelProperty("0表示正在审批，1表示审批通过，2标识审批拒绝")
    private Integer state;

    private String approvaler;

    private String changeuser;

    private String changeRealName;


}
