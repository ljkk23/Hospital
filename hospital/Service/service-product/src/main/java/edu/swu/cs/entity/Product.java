package edu.swu.cs.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 可预约时间表
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@Getter
@Setter
@ApiModel(value = "Product对象", description = "可预约时间表")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单id")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("医生编号")
    private Long doctorId;

    @ApiModelProperty("安排时间")
    private String date;


    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("创建时间")
      @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新人")
    private Long updateBy;

    @ApiModelProperty("更新时间")
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;

    @ApiModelProperty("是否已经上架")
    private String status;


    public Product(Long doctorId, String date) {
        this.doctorId = doctorId;
        this.date = date;
    }
}
