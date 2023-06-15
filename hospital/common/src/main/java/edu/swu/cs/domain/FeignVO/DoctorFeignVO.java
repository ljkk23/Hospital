package edu.swu.cs.domain.FeignVO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DoctorVo对象", description = "展示给前端")
public class DoctorFeignVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("医生职称")
    private String title;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("邮箱")
    private String email;


    @ApiModelProperty("手机号")
    private String phonenumber;

    @ApiModelProperty("用户性别（0男，1女，2未知）")
    private String sex;

    @ApiModelProperty("头像地址")
    private String avatar;

    @ApiModelProperty("如果是医生就是挂号费")
    private Integer amount;


}
