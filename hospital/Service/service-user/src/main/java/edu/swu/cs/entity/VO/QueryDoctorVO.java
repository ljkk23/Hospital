package edu.swu.cs.entity.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryDoctorVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long pageSize;
    private Long pageNum;
    private Long pageTotal;
    private List<DoctorVO> doctorVOList;
}
