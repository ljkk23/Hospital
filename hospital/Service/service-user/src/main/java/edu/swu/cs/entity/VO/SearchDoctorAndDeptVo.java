package edu.swu.cs.entity.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchDoctorAndDeptVo {
    private List<DoctorVO> doctorList;
    private List<String> deptNames;
}
