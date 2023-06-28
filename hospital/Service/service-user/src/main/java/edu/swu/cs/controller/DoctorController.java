package edu.swu.cs.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.swu.cs.annotation.systemLog;
import edu.swu.cs.domain.FeignVO.DoctorFeignVO;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.DeptCategory;
import edu.swu.cs.entity.Doctor;
import edu.swu.cs.entity.TestEnum;
import edu.swu.cs.entity.VO.DoctorVO;
import edu.swu.cs.domain.FeignVO.GetWareDoctorFeignVO;
import edu.swu.cs.entity.VO.GetDoctorByUserVO;
import edu.swu.cs.entity.VO.QueryDoctorVO;
import edu.swu.cs.entity.VO.TestVO;
import edu.swu.cs.entity.model.*;
import edu.swu.cs.mapper.DeptCategoryMapper;
import edu.swu.cs.service.IDeptCategoryService;
import edu.swu.cs.service.IDoctorRoleService;
import edu.swu.cs.service.IDoctorService;
import edu.swu.cs.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

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

    @Autowired
    private DeptCategoryMapper deptCategoryMapper;

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
//    @PreAuthorize("hasAnyAuthority('system:user:add')")
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

    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    public ResponseResult uoloadFile(@RequestParam("file") MultipartFile file) {
        return ResponseResult.okResult(doctorService.uploadFile(file));
    }

    @GetMapping(value = "/getImage",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(String filePath) throws Exception {
        String dirPath = System.getProperty("user.dir");

        File file = new File(dirPath+filePath);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

    /**
     * 逻辑删除医生
     * @param doctorId
     * @return
     */
    @GetMapping("/deletedDoctor")
    public ResponseResult deletedDoctor(Long doctorId){

        return doctorService.deleteDoctor(doctorId);
    }



    /**
     * 分页查询医生的信息
     * @param
     * @return
     */
    @systemLog(businessName = "分页获取医生的个人信息")
    @PostMapping("/getDoctorPageInfo")
    public ResponseResult getDoctor(@RequestBody InquireDoctorModel doctorModel){
        QueryDoctorVO pageDoctor = doctorService.getPageDoctor(doctorModel);
        return ResponseResult.okResult(pageDoctor);
    }

    @PostMapping("/searchDoctorOrDept")
    public ResponseResult searchDoctorOrDept(@RequestBody SearchDoctorAndDeptModel searchDoctorAndDeptModel){

        return doctorService.searchDoctorAndDept(searchDoctorAndDeptModel);
    }




    @GetMapping("FeignGetDoctorInfo")
    public DoctorVO FeignGetDoctorInfo(Long id){
        //根据id查询到医生
        Doctor doctor = doctorService.getById(id);
        //根据医生的deptid查询部门名字
        LambdaQueryWrapper<DeptCategory> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DeptCategory::getId,doctor.getDeptId());
        DeptCategory deptCategory = deptCategoryMapper.selectOne(lambdaQueryWrapper);

        DoctorVO doctorVO = BeanCopyUtils.copyBean(doctor, DoctorVO.class);
        doctorVO.setDeptName(deptCategory.getDeptName());

        return doctorVO;
    }

    @GetMapping("/getDoctorByFeign")
    public Doctor getDoctorByFeign(String userName){
        LambdaQueryWrapper<Doctor> LambdaQueryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper.eq(Doctor::getUserName,userName);
        return doctorService.getOne(LambdaQueryWrapper);
    }

    @GetMapping("/getDoctorInfo")
    public ResponseResult getDoctorInfo(String userName){
        LambdaQueryWrapper<Doctor> LambdaQueryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper.eq(Doctor::getUserName,userName);
        Doctor doctorOne = doctorService.getOne(LambdaQueryWrapper);
        GetDoctorByUserVO getDoctorByUserVO = BeanCopyUtils.copyBean(doctorOne, GetDoctorByUserVO.class);
        return ResponseResult.okResult(getDoctorByUserVO);
    }

    @GetMapping("/getDoctorByDeptByFeign")
    public List<GetWareDoctorFeignVO> getDoctorByDeptByFeign(String deptName){
        return doctorService.getDoctorByDeptByFeign(deptName);
    }

    //@GetMapping("/getDoctorIdByNameFeign")
    //    public Long getDoctorIdByNameFeign(@RequestParam("userName") String userName);

    @GetMapping("/getDoctorIdByNameFeign")
    public Long getDoctorIdByNameFeign(String userName){
        LambdaQueryWrapper<Doctor> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Doctor::getUserName,userName);
        Doctor one = doctorService.getOne(lambdaQueryWrapper);
        return one.getId();
    }

    @PostMapping("/checkRole")
    public ResponseResult checkRole(@RequestBody CheckRoleModel checkRoleModel){
        return doctorService.checkRole(checkRoleModel);
    }

    @GetMapping("/getDoctorRealNameByNameFeign")
    public DoctorFeignVO getDoctorRealNameByNameFeign(String userName){
        LambdaQueryWrapper<Doctor> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Doctor::getUserName,userName);
        Doctor one = doctorService.getOne(lambdaQueryWrapper);
        DoctorFeignVO doctorFeignVO = BeanCopyUtils.copyBean(one, DoctorFeignVO.class);


        DeptCategory deptCategory = deptCategoryMapper.selectById(one.getDeptId());
        doctorFeignVO.setDeptName(deptCategory.getDeptName());
        return doctorFeignVO;
    }

}

