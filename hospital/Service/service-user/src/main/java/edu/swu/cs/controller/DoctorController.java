package edu.swu.cs.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sun.xml.internal.bind.v2.TODO;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.domain.securityEntity.Role;
import edu.swu.cs.entity.Doctor;
import edu.swu.cs.entity.DoctorRole;
import edu.swu.cs.entity.VO.DoctorVO;
import edu.swu.cs.entity.VO.QueryDoctorVO;
import edu.swu.cs.entity.model.InquireDoctorModel;
import edu.swu.cs.entity.model.ModifyPasswordModel;
import edu.swu.cs.entity.model.UpdateDoctorInfoByUserModel;
import edu.swu.cs.entity.model.UpdateDoctorInfoModel;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.securityService.IRoleService;
import edu.swu.cs.service.IDoctorRoleService;
import edu.swu.cs.service.IDoctorService;
import edu.swu.cs.utils.BeanCopyUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 医院用户表 前端控制器
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@RestController
@Slf4j
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IDoctorRoleService doctorRoleService;

    /**
     * 医生注册账号
     * @param doctor
     * @return
     */
    @PreAuthorize("hasAnyAuthority('system:user:add')")
    @PostMapping("/addDoctorRole")
    public ResponseResult RegisterDoctor(@RequestBody Doctor doctor){
        return doctorService.addDoctorAndRole(doctor);
    }

    /**
     * modify Password
     * @param modifyPasswordModel
     * @return
     */
    @PostMapping("/modifyPassWord")
    public ResponseResult ModifyPassWord(@RequestBody ModifyPasswordModel modifyPasswordModel){
        return doctorService.modifyPassword(modifyPasswordModel);
    }

    /**
     * 更新医生的个人信息 by root
     * @param updateDoctorInfo
     * @return
     */
    @PreAuthorize("hasAnyAuthority('system:user:add')")
    @PostMapping("/updateDoctorInfoByRoot")
    public ResponseResult updateDoctorInfoByRoot(@RequestBody UpdateDoctorInfoModel updateDoctorInfo){
        return doctorService.updateDoctorInfoByRoot(updateDoctorInfo);
    }


    /**
     * 更新医生的个人信息 by user
     * @param updateDoctorInfo
     * @return
     */
    @PostMapping("/updateDoctorInfoByUser")
    public ResponseResult updateDoctorInfoByUser(@RequestBody UpdateDoctorInfoByUserModel updateDoctorInfo){
        return doctorService.updateDoctorInfoByUser(updateDoctorInfo);
    }

    /**
     * 逻辑删除医生
     * @param doctorId
     * @return
     */
    @PostMapping("/deletedDoctor")
    public ResponseResult deletedDoctor(@RequestBody Long doctorId){

        return doctorService.deleteDoctor(doctorId);
    }



    /**
     * 分页查询医生的信息
     * @param
     * @return
     */
    @PostMapping("/getDoctorPageInfo")
    public ResponseResult getDoctor(@RequestBody InquireDoctorModel doctorModel){
        QueryDoctorVO pageDoctor = doctorService.getPageDoctor(doctorModel);
        return ResponseResult.okResult(pageDoctor);
    }



    @GetMapping("FeignGetDoctorInfo")
    public DoctorVO FeignGetDoctorInfo(Long id){
        Doctor doctor = doctorService.getById(id);
        return BeanCopyUtils.copyBean(doctor, DoctorVO.class);
    }

    @GetMapping("/getDoctorByFeign")
    public Doctor getDoctorByFeign(String userName){
        LambdaQueryWrapper<Doctor> LambdaQueryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper.eq(Doctor::getUserName,userName);
        return doctorService.getOne(LambdaQueryWrapper);
    }
}

