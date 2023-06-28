package edu.swu.cs.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderListByDoctorVO {

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

}
