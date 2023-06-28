package edu.swu.cs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderListByDoctorIdModel {
    private String userName;
    private Long date;
    private Integer pageSize;
    private Integer pageNum;
    private  Integer pageTotal;
}
