package edu.swu.cs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author liujian
 * @since 2022-11-17
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_doctor_role")
@ApiModel(value = "DoctorRole对象", description = "")
public class DoctorRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;


}
