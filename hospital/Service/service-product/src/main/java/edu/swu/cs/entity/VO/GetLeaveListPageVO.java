package edu.swu.cs.entity.VO;

import edu.swu.cs.entity.DoctorLeave;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetLeaveListPageVO {
    private List<DoctorLeave> LeaveApprovalList;
    private Long pageSize;
    private Long pageNum;
    private Long pageTotal;
}
