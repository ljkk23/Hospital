package edu.swu.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Patient;
import edu.swu.cs.entity.model.GetPatientInfoModel;
import edu.swu.cs.entity.model.ModifyPatientInfoModel;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.mapper.PatientMapper;
import edu.swu.cs.service.IPatientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.swu.cs.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 就诊人表 服务实现类
 * </p>
 *
 * @author liujian
 */
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements IPatientService {

    @Override
    public ResponseResult addPatient(Patient patient) {
        //检查是否已经存在
        LambdaQueryWrapper<Patient> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Patient::getUserId,patient.getUserId())
                .eq(Patient::getUserName,patient.getUserName());
        if(!Objects.isNull(this.baseMapper.selectOne(lambdaQueryWrapper))){
            return ResponseResult.errorResult(500,"该用户已经存在");
        }
        if (!this.save(patient)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"插入数据库出错");
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult modifyPatient(ModifyPatientInfoModel modifyPatientInfoModel) {
        Patient patient = BeanCopyUtils.copyBean(modifyPatientInfoModel, Patient.class);
        if (!this.updateById(patient)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATABASE_ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getPatient(GetPatientInfoModel getPatientInfoModel) {
        LambdaQueryWrapper<Patient> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Patient::getUserId,getPatientInfoModel.getUserId());
        List<Patient> patients = this.baseMapper.selectList(lambdaQueryWrapper);
        return ResponseResult.okResult(patients);
    }
}
