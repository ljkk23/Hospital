package edu.swu.cs.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalLeaveModel {

    private Integer id;
    private String userName;

    @ApiModelProperty("0表示正在审批，1表示审批通过，2标识审批拒绝")
    private Integer state;

    private String approvaler;
}
