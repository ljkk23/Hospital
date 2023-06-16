package edu.swu.cs.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import edu.swu.cs.domain.FeignVO.PatientVo;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Doctor;
import edu.swu.cs.entity.Patient;
import edu.swu.cs.entity.VO.DoctorVO;
import edu.swu.cs.entity.model.DeletePatientModel;
import edu.swu.cs.entity.model.GetPatientInfoModel;
import edu.swu.cs.entity.model.ModifyPatientInfoModel;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.service.IPatientService;
import edu.swu.cs.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 就诊人表 前端控制器
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private IPatientService patientService;

    /**
     * 新增一个就诊人
     * @param patient
     * @return
     */
    @PostMapping("/addPatient")
    public ResponseResult addPatient(@RequestBody Patient patient){
        return patientService.addPatient(patient);
    }

    /**
     *
     * 逻辑删除就诊人
     * @param deletePatientModel
     * @return
     */
    @PostMapping("/deletePatient")
    public ResponseResult deletePatient(@RequestBody DeletePatientModel deletePatientModel){
        LambdaUpdateWrapper<Patient> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Patient::getId,deletePatientModel.getId());
        if(!patientService.remove(lambdaUpdateWrapper)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATABASE_ERROR);
        }
        return ResponseResult.okResult();
    }

    /**
     * 修改就诊人信息
     * @param modifyPatientInfoModel
     * @return
     */
    @PostMapping("/modifyPatientInfo")
    public ResponseResult modifyPatientInfo(@RequestBody ModifyPatientInfoModel modifyPatientInfoModel){
        return patientService.modifyPatient(modifyPatientInfoModel);
    }

    /**
     * 根据userID查询就诊人信息
     * @param getPatientInfoModel
     * @return
     */
    @PostMapping("/getPatientInfo")
    public ResponseResult getPatientInfo(@RequestBody GetPatientInfoModel getPatientInfoModel){
        return patientService.getPatient(getPatientInfoModel);
    }

    @GetMapping("FeignGetPatientInfo")
    public PatientVo FeignGetPatientInfo(Long id){
        Patient patient = patientService.getById(id);
        return BeanCopyUtils.copyBean(patient, PatientVo.class);
    }

}

