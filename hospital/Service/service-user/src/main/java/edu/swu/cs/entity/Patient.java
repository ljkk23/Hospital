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
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 就诊人表
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("sys_patient")
@ApiModel(value = "Patient对象", description = "就诊人表")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("归属于哪一个用户")
    private Long userId;

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


    public Patient(Long userId, String cardId, String phonenumber, String sex, Integer age) {
        this.userId = userId;
        this.cardId = cardId;
        this.phonenumber = phonenumber;
        this.sex = sex;
        this.age = age;
    }
}
