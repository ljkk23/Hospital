package edu.swu.cs.service.impl;

import edu.swu.cs.entity.Patient;
import edu.swu.cs.mapper.PatientMapper;
import edu.swu.cs.service.IPatientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 就诊人表 服务实现类
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements IPatientService {

}
