package edu.swu.cs.service;

import edu.swu.cs.controller.DoctorController;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Doctor;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.swu.cs.entity.VO.DoctorVO;
import edu.swu.cs.entity.VO.QueryDoctorVO;
import edu.swu.cs.entity.model.InquireDoctorModel;

import java.util.List;

/**
 * <p>
 * 医院用户表 服务类
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
public interface IDoctorService extends IService<Doctor> {

    QueryDoctorVO getPageDoctor(InquireDoctorModel doctorModel);



}
