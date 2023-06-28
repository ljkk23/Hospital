package edu.swu.cs.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("部门id")
    private Long deptId;

    private String deptName;

    @ApiModelProperty("医生职称")
    private String title;

    @ApiModelProperty("医生姓名")
    private String doctorRealName;


    @ApiModelProperty("医生邮箱")
    private String doctorEmail;

    @ApiModelProperty("医生手机号")
    private String doctorPhonenumber;

    @ApiModelProperty("医生用户性别（0男，1女，2未知）")
    private String doctorSex;

    @ApiModelProperty("医生头像地址")
    private String doctorAvatar;

    @ApiModelProperty("挂号费")
    private Integer amount;


    @ApiModelProperty("用户名")
    private String patientUserName;

    @ApiModelProperty("就诊人身份证号码")
    private String patientCardId;

    @ApiModelProperty("就诊人手机号")
    private String patientPhonenumber;

    @ApiModelProperty("就诊人用户性别（0男，1女，2未知）")
    private String patientSex;

    @ApiModelProperty("就诊人年龄")
    private Integer patientAge;
    @ApiModelProperty("过往病史")
    private String medicalHistory;



    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("订单安排时间")
    private Long date;

    private Integer offsetTime;

    @ApiModelProperty("订单创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("订单状态，0代表已下单未支付，1代表已支付未服务，2代表已服务，3代表已退号")
    private Integer orderStatus;

    private String opinions;



}
