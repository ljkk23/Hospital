package edu.swu.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Doctor;
import edu.swu.cs.entity.VO.DoctorVO;
import edu.swu.cs.entity.VO.QueryDoctorVO;
import edu.swu.cs.entity.model.InquireDoctorModel;
import edu.swu.cs.mapper.DoctorMapper;
import edu.swu.cs.service.IDoctorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.swu.cs.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 医院用户表 服务实现类
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements IDoctorService {

    @Override
    public QueryDoctorVO getPageDoctor(InquireDoctorModel doctorModel) {
        Page<Doctor> page=new Page<>(doctorModel.getPageNum(), doctorModel.getPageSize());
        LambdaQueryWrapper<Doctor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper
                .eq(doctorModel.getUserName()!=null&& !doctorModel.getUserName().trim().isEmpty(),
                Doctor::getUserName,doctorModel.getUserName())
                .eq(doctorModel.getTitle()!=null && !doctorModel.getTitle().trim().isEmpty()
                ,Doctor::getTitle,doctorModel.getTitle())
                .eq(doctorModel.getDeptId()!=null,Doctor::getDeptId,doctorModel.getDeptId());
        Page<Doctor> doctorPage = this.baseMapper.selectPage(page, queryWrapper);

        List<Doctor> records = doctorPage.getRecords();
        List<DoctorVO> doctorVOS = BeanCopyUtils.copyBeanList(records, DoctorVO.class);

        return new QueryDoctorVO(doctorPage.getSize(),doctorPage.getCurrent(),doctorPage.getTotal(),doctorVOS);
    }


}
