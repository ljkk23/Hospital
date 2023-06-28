package edu.swu.cs.entity.VO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetWareProDuctListVO {


    private Long id;

    @ApiModelProperty("医生职称")
    private String title;

    @ApiModelProperty("真实姓名")
    private String realName;



    @ApiModelProperty("如果是医生就是挂号费")
    private Integer amount;

    private Integer count;

    @ApiModelProperty("医生的简介")
    private String introduce="sss";

    private String sex=null;

    private Long productId;

    private String avatar="";

}
