package edu.swu.cs.service;

import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Patient;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.swu.cs.entity.model.GetPatientInfoModel;
import edu.swu.cs.entity.model.ModifyPatientInfoModel;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * <p>
 * 就诊人表 服务类
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
public interface IPatientService extends IService<Patient> {

    ResponseResult addPatient(Patient patient);

    ResponseResult modifyPatient(ModifyPatientInfoModel modifyPatientInfoModel);

    ResponseResult getPatient(GetPatientInfoModel getPatientInfoModel);
}
