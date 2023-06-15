package edu.swu.cs.controller;


import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Doctor;
import edu.swu.cs.entity.model.AddDoctorRoleModel;
import edu.swu.cs.service.IDoctorRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liujian
 * @since 2022-11-17
 */
@RestController
@RequestMapping("/doctor-role")
public class DoctorRoleController {
    @Autowired
    private IDoctorRoleService doctorRoleService;

}

