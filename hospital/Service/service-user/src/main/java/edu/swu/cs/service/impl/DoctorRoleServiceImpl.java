package edu.swu.cs.service.impl;

import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Doctor;
import edu.swu.cs.entity.DoctorRole;
import edu.swu.cs.entity.model.AddDoctorRoleModel;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.mapper.DoctorRoleMapper;
import edu.swu.cs.service.IDoctorRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujian
 * @since 2022-11-17
 */
@Service
public class DoctorRoleServiceImpl extends ServiceImpl<DoctorRoleMapper, DoctorRole> implements IDoctorRoleService {


    @Override
    public ResponseResult addDoctorRole(AddDoctorRoleModel doctor) {
        boolean saveDoctorAndRole = this.save(new DoctorRole(doctor.getDoctorId(), Long.valueOf(doctor.getType())));
        if (!saveDoctorAndRole){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"添加医生权限出错");
        }
        return ResponseResult.okResult();
    }
}
