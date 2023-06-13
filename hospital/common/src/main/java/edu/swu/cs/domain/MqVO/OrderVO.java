package edu.swu.cs.domain.MqVO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class OrderVO implements Serializable {
    private static final long serialVersionUID = 1L;



    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("就诊人id")
    private Long patientId;

    @ApiModelProperty("订单的唯一id")
    private String orderId;

    @ApiModelProperty("类型")
    private String type;

}
