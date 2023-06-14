package edu.swu.cs.service;

import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Doctor;
import edu.swu.cs.entity.DoctorRole;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.swu.cs.entity.model.AddDoctorRoleModel;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liujian
 * @since 2022-11-17
 */
public interface IDoctorRoleService extends IService<DoctorRole> {
    ResponseResult addDoctorRole(AddDoctorRoleModel doctor);
}
