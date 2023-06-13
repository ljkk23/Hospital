package edu.swu.cs.client;

import edu.swu.cs.client.impl.UserClientImpl;
import edu.swu.cs.domain.FeignVO.DoctorFeignVO;
import edu.swu.cs.domain.FeignVO.PatientVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "service-user",fallback = UserClientImpl.class)
public interface UserClient {

    //远程调用医生的信息
    @GetMapping("/service-user/doctor/FeignGetDoctorInfo")
    public DoctorFeignVO FeignGetDoctorInfo(@RequestParam(value = "id") Long id);

    @GetMapping("/service-user/patient/FeignGetPatientInfo")
    public PatientVo FeignGetPatientInfo(@RequestParam(value = "id") Long id);

}