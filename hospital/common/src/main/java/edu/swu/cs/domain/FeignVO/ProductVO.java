package edu.swu.cs.domain.FeignVO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ProductVO对象", description = "展示给前端")
public class ProductVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("安排时间")
    private String date;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("医生编号")
    private Long doctorId;


}
