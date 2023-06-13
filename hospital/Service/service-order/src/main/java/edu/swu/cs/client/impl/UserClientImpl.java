package edu.swu.cs.client.impl;

import edu.swu.cs.client.UserClient;
import edu.swu.cs.domain.FeignVO.DoctorFeignVO;
import edu.swu.cs.domain.FeignVO.PatientVo;
import org.springframework.stereotype.Component;

@Component
public class UserClientImpl implements UserClient {


    @Override
    public DoctorFeignVO FeignGetDoctorInfo(Long id) {
        return new DoctorFeignVO();
    }

    @Override
    public PatientVo FeignGetPatientInfo(Long id) {
        return null;
    }
}