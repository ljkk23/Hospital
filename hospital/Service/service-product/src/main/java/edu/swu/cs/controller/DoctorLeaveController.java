package edu.swu.cs.controller;


import edu.swu.cs.client.DoctorClient;
import edu.swu.cs.domain.FeignVO.DoctorFeignVO;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.ApprovalLeaveModel;
import edu.swu.cs.entity.DoctorLeave;
import edu.swu.cs.service.IDoctorLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 医生的请假表 前端控制器
 * </p>
 *
 * @author liujian
 * @since 2023-06-21
 */
@RestController
@RequestMapping("/doctor-leave")
public class DoctorLeaveController {

    @Autowired
    private IDoctorLeaveService doctorLeaveService;

    @Autowired
    private DoctorClient doctorClient;

    @PostMapping ("/submitLeave")
    public ResponseResult submitDoctorLeave(@RequestBody DoctorLeave doctorLeave){
        DoctorFeignVO doctor = doctorClient.getDoctorRealNameByNameFeign(doctorLeave.getUserName());
        doctorLeave.setDeptName(doctor.getDeptName());
        doctorLeave.setRealName(doctor.getRealName());

        DoctorFeignVO doctorChange = doctorClient.getDoctorRealNameByNameFeign(doctorLeave.getChangeuser());
        doctorLeave.setChangeuser(doctorLeave.getChangeuser());
        doctorLeave.setChangeRealName(doctorChange.getRealName());
        return ResponseResult.okResult(doctorLeaveService.save(doctorLeave));
    }

    @GetMapping("/getAllLeave")
    public ResponseResult getAllLeave(Integer pageSize,Integer pageNum){
        return ResponseResult.okResult(doctorLeaveService.getLeaveAll(pageSize,pageNum));
    }

    @PostMapping ("/approvalLeave")
    public ResponseResult approvalLeave(@RequestBody ApprovalLeaveModel approvalLeaveModel){
        return ResponseResult.okResult(doctorLeaveService.approveLeave(approvalLeaveModel));
    }

    @GetMapping("/getAllLeaveByDoctor")
    public ResponseResult getAllLeaveByDoctor(String userName,Integer pageSize,Integer pageNum){
        return ResponseResult.okResult(doctorLeaveService.getAllLeaveByDoctor(userName,pageSize,pageNum));
    }

}

