package edu.swu.cs.client;

import edu.swu.cs.client.impl.UserClientImpl;
import edu.swu.cs.domain.FeignVO.DoctorFeignVO;
import edu.swu.cs.domain.FeignVO.PatientVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "service-ware")
public interface WareClient {


    //远程调用锁定库存
    @GetMapping("/service-ware/ware/lockWare")
    Boolean lockWare(@RequestParam(value = "productID") Long productID);

}
