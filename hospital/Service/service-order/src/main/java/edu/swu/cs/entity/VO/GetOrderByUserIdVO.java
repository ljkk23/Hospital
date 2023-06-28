package edu.swu.cs.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderByUserIdVO {

    @ApiModelProperty("医生用户名")
    private String doctorUserName;




    @ApiModelProperty("用户名")
    private String patientUserName;



    @ApiModelProperty("就诊人年龄")
    private Integer patientAge;



    @ApiModelProperty("订单编号")
    private Long orderId;

    @ApiModelProperty("订单安排时间")
    private String date;

    @ApiModelProperty("订单状态，0代表已下单未支付，1代表已支付未服务，2代表已服务，3代表已退号")
    private Integer orderStatus;
}
