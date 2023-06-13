package edu.swu.cs.entity.VO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ApiModel(value = "DeptCategoryVO对象", description = "科室分类")
public class DeptCategoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("父极分类")
    private Long parentId;

    @ApiModelProperty("部门名字")
    private String deptName;
    @ApiModelProperty("子菜单")
    private List<DeptCategoryVO> childCategory;



}
