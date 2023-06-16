package edu.swu.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.domain.securityEntity.UserDetailsImpl;
import edu.swu.cs.entity.Doctor;
import edu.swu.cs.entity.DoctorRole;
import edu.swu.cs.entity.VO.DoctorVO;
import edu.swu.cs.entity.VO.QueryDoctorVO;
import edu.swu.cs.entity.VO.SearchDoctorAndDeptVo;
import edu.swu.cs.entity.model.*;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.mapper.DoctorMapper;
import edu.swu.cs.service.IDeptCategoryService;
import edu.swu.cs.service.IDoctorRoleService;
import edu.swu.cs.service.IDoctorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.swu.cs.utils.BeanCopyUtils;
import edu.swu.cs.utils.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 医院用户表 服务实现类
 * </p>
 *
 * @author liujian
 *
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements IDoctorService {
    @Autowired
    private IDoctorRoleService doctorRoleService;
    @Autowired
    private IDeptCategoryService deptCategoryService;

    @Override
    public QueryDoctorVO getPageDoctor(InquireDoctorModel doctorModel) {
        Page<Doctor> page=new Page<>(doctorModel.getPageNum(), doctorModel.getPageSize());
        LambdaQueryWrapper<Doctor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper
                .eq(doctorModel.getRealName()!=null&& !doctorModel.getRealName().trim().isEmpty(),
                Doctor::getRealName,doctorModel.getRealName())
                .eq(doctorModel.getTitle()!=null && !doctorModel.getTitle().trim().isEmpty()
                ,Doctor::getTitle,doctorModel.getTitle())
                .eq(doctorModel.getDeptId()!=null,Doctor::getDeptId,doctorModel.getDeptId());
        Page<Doctor> doctorPage = this.baseMapper.selectPage(page, queryWrapper);

        List<Doctor> records = doctorPage.getRecords();
        List<DoctorVO> doctorVOS = BeanCopyUtils.copyBeanList(records, DoctorVO.class);

        return new QueryDoctorVO(doctorPage.getSize(),doctorPage.getCurrent(),doctorPage.getTotal(),doctorVOS);
    }
    @Transactional
    @Override
    public ResponseResult addDoctorAndRole(Doctor doctor) {
        try {
            //保存医生的基本信息
            boolean saveDoctor = this.save(doctor);
            //查出doctor的主键id,然后保存在role表中
            LambdaQueryWrapper<Doctor> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(Doctor::getUserName,doctor.getUserName());
            Doctor one = this.getOne(queryWrapper);

            doctorRoleService.save(new DoctorRole(one.getId(),one.getType()));
            if (!saveDoctor){
                return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"插入数据库出错");
            }
        }catch (Exception e){
            log.error("add doctor err:%s",e);
        }


        return ResponseResult.okResult("注册成功");
    }

    @Override
    public ResponseResult modifyPassword(ModifyPasswordModel modifyPasswordModel) {
        //根据security context去判断用户对象是否正确
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        if(!Objects.equals(userDetails.getUsername(),modifyPasswordModel.getUserName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.WRONG_OPERATOR);
        }


        LambdaUpdateWrapper<Doctor> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Doctor::getUserName,modifyPasswordModel.getUserName())
                .set(Doctor::getPassword,modifyPasswordModel.getPassword());
        if(!this.update(updateWrapper)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATABASE_ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateDoctorInfoByRoot(UpdateDoctorInfoModel updateDoctorInfoModel) {
        try {
            //先查询用户是否存在
            LambdaQueryWrapper<Doctor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Doctor::getUserName, updateDoctorInfoModel.getUserName());
            Doctor one = this.getOne(queryWrapper);
            if (Objects.isNull(one)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.NO_USER);
            }

            BeanUtils.copyProperties(updateDoctorInfoModel, one);
            if (!this.updateById(one)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.DATABASE_ERROR);
            }
        }catch (Exception e){
            log.error("更新医生信息失败%s",e);
            return ResponseResult.errorResult(AppHttpCodeEnum.DATABASE_ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateDoctorInfoByUser(UpdateDoctorInfoByUserModel updateDoctorInfoModel) {
        try {
            //先查询用户是否存在
            LambdaQueryWrapper<Doctor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Doctor::getUserName, updateDoctorInfoModel.getUserName());
            Doctor one = this.getOne(queryWrapper);
            if (Objects.isNull(one)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.NO_USER);
            }

            BeanUtils.copyProperties(updateDoctorInfoModel, one);
            if (!this.updateById(one)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.DATABASE_ERROR);
            }
        }catch (Exception e){
            log.error("更新医生信息失败%s",e);
            return ResponseResult.errorResult(AppHttpCodeEnum.DATABASE_ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteDoctor(Long doctorId) {
        try {
            if(!this.removeById(doctorId)){
                return ResponseResult.errorResult(AppHttpCodeEnum.DATABASE_ERROR);
            }
        }catch (Exception e){
            log.error("remove doctor failed{}",e);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult searchDoctorAndDept(SearchDoctorAndDeptModel searchDoctorAndDeptModel) {
        //查询doctor
        LambdaQueryWrapper<Doctor> docQueryWrapper=new LambdaQueryWrapper<>();
        docQueryWrapper.like(Doctor::getRealName,searchDoctorAndDeptModel.getData())
                .or().like(Doctor::getIntroduce,searchDoctorAndDeptModel.getData());
        List<Doctor> doctorList = this.getBaseMapper().selectList(docQueryWrapper);
        List<DoctorVO> doctorVOS = BeanCopyUtils.copyBeanList(doctorList, DoctorVO.class);

        //查询科室
        List<String> deptNameList = deptCategoryService.searchDept(searchDoctorAndDeptModel.getData());

        SearchDoctorAndDeptVo searchDoctorAndDeptVo=new SearchDoctorAndDeptVo(doctorVOS,deptNameList);

        return ResponseResult.okResult(searchDoctorAndDeptVo);
    }


}
