package edu.swu.cs.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sun.xml.internal.bind.v2.TODO;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.domain.securityEntity.Role;
import edu.swu.cs.entity.Doctor;
import edu.swu.cs.entity.DoctorRole;
import edu.swu.cs.entity.VO.DoctorVO;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.securityService.IRoleService;
import edu.swu.cs.service.IDoctorRoleService;
import edu.swu.cs.service.IDoctorService;
import edu.swu.cs.utils.BeanCopyUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IDoctorRoleService doctorRoleService;

    @ApiImplicitParams({
            @ApiImplicitParam(name="deptId",value="部门id",dataType="Long",example="2"),
            @ApiImplicitParam(name="title",value="医生职称",dataType="String")}
    )
    @PreAuthorize("hasAnyAuthority('system:user:add')")
    @PostMapping("/addDoctor")
    //TODO:实现事务增加doctor和新增doctor对应的角色
    public ResponseResult addDoctor(Doctor doctor){

        doctor.setPassword(new BCryptPasswordEncoder().encode(doctor.getPassword()));
        boolean saveDoctor = doctorService.save(doctor);
        boolean saveDoctorAndRole = doctorRoleService.save(new DoctorRole(doctor.getId(), Long.valueOf(doctor.getType())));
        if (!(saveDoctor || saveDoctorAndRole)){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"插入数据库出错");
        }
        return ResponseResult.okResult();
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

