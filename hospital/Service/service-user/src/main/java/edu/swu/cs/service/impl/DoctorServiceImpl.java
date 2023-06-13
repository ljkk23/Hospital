package edu.swu.cs.service.impl;

import edu.swu.cs.entity.Doctor;
import edu.swu.cs.mapper.DoctorMapper;
import edu.swu.cs.service.IDoctorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
