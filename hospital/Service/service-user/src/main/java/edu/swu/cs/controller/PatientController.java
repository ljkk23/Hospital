package edu.swu.cs.controller;


import edu.swu.cs.domain.FeignVO.PatientVo;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Doctor;
import edu.swu.cs.entity.Patient;
import edu.swu.cs.entity.VO.DoctorVO;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.service.IPatientService;
import edu.swu.cs.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/addPatient")
    public ResponseResult addPatient(Patient patient){
        if (!patientService.save(patient)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"插入数据库出错");
        }
        return ResponseResult.okResult();
    }

    @GetMapping("FeignGetPatientInfo")
    public PatientVo FeignGetPatientInfo(Long id){
        Patient patient = patientService.getById(id);
        return BeanCopyUtils.copyBean(patient, PatientVo.class);
    }

}

