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
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "PatientVo对象", description = "展示给前端")
public class PatientVo implements Serializable {
    private static final long serialVersionUID = 1L;

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
    @ApiModelProperty("过往病史")
    private String medicalHistory;



}
