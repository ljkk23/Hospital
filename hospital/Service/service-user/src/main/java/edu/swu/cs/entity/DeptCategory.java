package edu.swu.cs.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 科室分类表
 * </p>
 *
 * @author liujian
 * @since 2022-11-13
 */
@Getter
@Setter
@TableName("sys_dept_category")
@ApiModel(value = "DeptCategory对象", description = "科室分类表")
public class DeptCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("父极分类")
    private Long parentId;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("更新时间")
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;

    private String deptName;

      @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
      //1表示门诊分类
      private Integer type;


}
