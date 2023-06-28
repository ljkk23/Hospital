package edu.swu.cs.client;

import edu.swu.cs.domain.FeignVO.DoctorFeignVO;
import edu.swu.cs.domain.FeignVO.GetWareDoctorFeignVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-user")
public interface DoctorClient {
    // public List<Long> getDoctorByDeptByFeign(String deptName){
    //    @GetMapping("/getDoctorByDeptByFeign")
    //    public List<GetWareDoctorFeignVO> getDoctorByDeptByFeign(String deptName){
    @GetMapping("/service-user/doctor/getDoctorByDeptByFeign")
    public List<GetWareDoctorFeignVO> getDoctorByDeptByFeign(@RequestParam("deptName") String deptName);

    @GetMapping("/service-user/doctor/getDoctorIdByNameFeign")
    public Long getDoctorIdByNameFeign(@RequestParam("userName") String userName);

    @GetMapping("/service-user/doctor/getDoctorRealNameByNameFeign")
    public DoctorFeignVO getDoctorRealNameByNameFeign(@RequestParam("userName") String userName);

}
