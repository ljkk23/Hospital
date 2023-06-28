package edu.swu.cs.client;

import edu.swu.cs.client.impl.ProductClientImpl;
import edu.swu.cs.client.impl.UserClientImpl;
import edu.swu.cs.domain.FeignVO.DoctorFeignVO;
import edu.swu.cs.domain.FeignVO.ProductVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-product",fallback = ProductClientImpl.class)
public interface ProductClient {
    @GetMapping("/service-product/product/FeignGetProductInfo")
    public ProductVO FeignGetProductInfo(@RequestParam(value = "id") Long id);


    @GetMapping("/service-product/product/getProductIdByDoctorAndDateByFeign")
    public List<Long> getProductIdByDoctorAndDate(@RequestParam(value = "userName")String userName, @RequestParam(value = "date") Long date);
}
