package edu.swu.cs.service;

import edu.swu.cs.entity.ApprovalLeaveModel;
import edu.swu.cs.entity.DoctorLeave;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.swu.cs.entity.VO.GetLeaveListPageVO;

import java.util.List;

/**
 * <p>
 * 医生的请假表 服务类
 * </p>
 *
 * @author liujian
 * @since 2023-06-21
 */
public interface IDoctorLeaveService extends IService<DoctorLeave> {
    public GetLeaveListPageVO getLeaveAll(Integer pageSize,Integer pageNum);

    Boolean approveLeave(ApprovalLeaveModel approvalLeaveModel);

    GetLeaveListPageVO getAllLeaveByDoctor(String userName,Integer pageSize,Integer pageNum);
}
