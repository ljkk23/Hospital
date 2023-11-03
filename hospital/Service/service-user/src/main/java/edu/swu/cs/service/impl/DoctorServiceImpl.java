package edu.swu.cs.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import edu.swu.cs.Exception.SystemException;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.domain.securityEntity.UserDetailsImpl;
import edu.swu.cs.entity.DeptCategory;
import edu.swu.cs.entity.Doctor;
import edu.swu.cs.entity.DoctorRole;
import edu.swu.cs.entity.VO.DoctorVO;
import edu.swu.cs.domain.FeignVO.GetWareDoctorFeignVO;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    private DoctorMapper doctorMapper;

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

        for (DoctorVO doctorVO : doctorVOS){
            String deptName = deptCategoryService.getById(doctorVO.getDeptId()).getDeptName();
            doctorVO.setDeptName(deptName);
        }
        return new QueryDoctorVO(doctorPage.getSize(),doctorPage.getCurrent(),Math.ceil((double) doctorPage.getTotal()/doctorModel.getPageSize()),doctorVOS);
    }
    @Transactional
    @Override
    public ResponseResult addDoctorAndRole(Doctor doctor) {
        try {
            //保存医生的基本信息
            doctor.setPassword(MD5Util.encode(doctor.getPassword()));
            boolean saveDoctor = this.save(doctor);
            //查出doctor的主键id,然后保存在role表中
            LambdaQueryWrapper<Doctor> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(Doctor::getUserName,doctor.getUserName());
            Doctor one = this.getOne(queryWrapper);

            doctorRoleService.save(new DoctorRole(one.getId(), 2L));
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

        String passwordEncode = MD5Util.encode(modifyPasswordModel.getPassword());
        LambdaUpdateWrapper<Doctor> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Doctor::getUserName,modifyPasswordModel.getUserName())
                .set(Doctor::getPassword,passwordEncode);
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
            LambdaUpdateWrapper<Doctor> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Doctor::getId,doctorId);
            if(!this.remove(lambdaUpdateWrapper)){
                return ResponseResult.errorResult(AppHttpCodeEnum.DATABASE_ERROR);
            }
        }catch (Exception e){
            log.error("remove doctor failed{}",e);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult searchDoctorAndDept(SearchDoctorAndDeptModel searchDoctorAndDeptModel) {
        try {


            //查询doctor
            LambdaQueryWrapper<Doctor> docQueryWrapper = new LambdaQueryWrapper<>();
            docQueryWrapper.like(Doctor::getRealName, searchDoctorAndDeptModel.getData())
                    .or().like(Doctor::getIntroduce, searchDoctorAndDeptModel.getData());
            List<Doctor> doctorList = this.getBaseMapper().selectList(docQueryWrapper);
            List<DoctorVO> doctorVOS = BeanCopyUtils.copyBeanList(doctorList, DoctorVO.class);

            //查询科室
            List<String> deptNameList = deptCategoryService.searchDept(searchDoctorAndDeptModel.getData());

            SearchDoctorAndDeptVo searchDoctorAndDeptVo = new SearchDoctorAndDeptVo(doctorVOS, deptNameList);

            return ResponseResult.okResult(searchDoctorAndDeptVo);
        }catch (Exception e){
            return ResponseResult.errorResult(500,"没有该数据");
        }
    }

    @Override
    public List<GetWareDoctorFeignVO> getDoctorByDeptByFeign(String deptName) {
        //根据科室名称查询科室id
        LambdaQueryWrapper<DeptCategory> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DeptCategory::getDeptName,deptName);
        DeptCategory deptCategory = deptCategoryService.getOne(lambdaQueryWrapper);
        //根据科室id查询医生列表
        LambdaQueryWrapper<Doctor> doctorLambdaQueryWrapper=new LambdaQueryWrapper<>();
        doctorLambdaQueryWrapper.eq(Doctor::getDeptId,deptCategory.getId());
        List<Doctor> doctorList = this.list(doctorLambdaQueryWrapper);

        List<GetWareDoctorFeignVO> doctorIdList = doctorList.stream().map(x->{
            return new GetWareDoctorFeignVO(x.getId(),x.getTitle(),x.getAmount(),x.getRealName(),x.getIntroduce(), x.getSex(), x.getAvatar());
        }).collect(Collectors.toList());

        return doctorIdList;

    }

    @Override
    public ResponseResult checkRole(CheckRoleModel checkRoleModel) {
        //根据security context去判断用户对象是否正确
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        String username = userDetails.getUsername();
        List<String> roleHTML = doctorMapper.selectByDoctorIdHTML(username);
        return ResponseResult.okResult(roleHTML.contains(checkRoleModel.getHtml()));
    }

    @Override
    public String uploadFile(MultipartFile file) {
        // 首先校验图片格式
        List<String> imageType = Lists.newArrayList("jpg","jpeg", "png", "bmp", "gif");
        // 获取文件名，带后缀
        String originalFilename = file.getOriginalFilename();
        // 获取文件的后缀格式
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (imageType.contains(fileSuffix)) {
            // 只有当满足图片格式时才进来，重新赋图片名，防止出现名称重复的情况
            String newFileName = UUID.randomUUID() + originalFilename;
            // 该方法返回的为当前项目的工作目录，即在哪个地方启动的java线程
            String dirPath = System.getProperty("user.dir");
            String path = File.separator + "uploadImg" + File.separator + newFileName;
            File destFile = new File(dirPath + path);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            try {
                file.transferTo(destFile);
                // 将相对路径返回给前端

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
                String username = userDetails.getUsername();
                //将头像路径存在数据库中
                LambdaUpdateWrapper<Doctor> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
                lambdaUpdateWrapper.eq(Doctor::getUserName,username)
                        .set(Doctor::getAvatar,path);
                if (!this.update(lambdaUpdateWrapper)){
                    throw new RuntimeException("保存头像路径到数据库失败");
                }
                return path;
            } catch (IOException e) {
                log.error("upload pic error");
                return null;
            }
        } else {
            // 非法文件
            log.error("the picture's suffix is illegal");
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
    }


}
