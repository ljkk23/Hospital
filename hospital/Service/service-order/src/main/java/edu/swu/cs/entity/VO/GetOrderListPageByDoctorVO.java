package edu.swu.cs.entity.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderListPageByDoctorVO {
    private Long pageSize;
    private Long pageNum;
    private double pageTotal;
    private List<GetOrderListByDoctorVO> orderList;
}
