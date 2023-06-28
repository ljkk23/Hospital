package edu.swu.cs.client;

import edu.swu.cs.domain.FeignVO.GetWareListByProductIDListVO;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Ware;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-ware")
public interface WareClient {

    @GetMapping("/service-ware/ware/getWareByProductId")
    public Integer getWareByProductId(@RequestParam(value = "productID") Long productID);

    @GetMapping("/service-ware/ware/updateHotOrderWare")
    public Boolean updateHotOrderWare(@RequestParam(value = "productID") Long productID, @RequestParam(value = "Count") Integer Count);

    //@GetMapping("getWareListByProductIdByFeign")
    //    public List<GetWareListByProductIDListVO> getWareListByProductIdByFeign(List<Long> productIDs){
    @GetMapping("/service-ware/ware/getWareListByProductIdByFeign")
    public List<GetWareListByProductIDListVO> getWareListByProductIdByFeign(@RequestParam(value = "productIDList") List<Long> productIDList);


    @PostMapping("/service-ware/ware/addWareListByFeign")
    public Boolean addWareListByFeign(@RequestBody List<Ware> wareList);
}
