package edu.swu.cs.client;

import edu.swu.cs.domain.FeignVO.DoctorFeignVO;
import edu.swu.cs.domain.FeignVO.UserFeignVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "service-user")
public interface UserClient {

    @GetMapping("/service-user/doctor/getDoctorByFeign")
    DoctorFeignVO getDoctorByFeign(@RequestParam(value = "userName") String userName);

    @GetMapping("/service-user/user/getUserByFeign")
    UserFeignVO getUserByFeign(@RequestParam(value = "userName") String userName);

}
