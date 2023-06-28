package edu.swu.cs.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.swu.cs.client.DoctorClient;
import edu.swu.cs.client.WareClient;
import edu.swu.cs.domain.FeignVO.GetWareDoctorFeignVO;
import edu.swu.cs.domain.FeignVO.ProductVO;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.DeleteProductModel;
import edu.swu.cs.entity.Product;
import edu.swu.cs.entity.VO.GetDoctorsByDateAndArrangeVO;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.service.IProductService;
import edu.swu.cs.utils.BeanCopyUtils;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 可预约时间表 前端控制器
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private WareClient wareClient;

    @Autowired
    private DoctorClient doctorClient;
    @PostMapping("/addProduct")
    public ResponseResult addProduct(@RequestBody Product product){
        return productService.saveProductAndWare(product);
    }

    @PostMapping("/deleteProduct")
    public ResponseResult deleteProduct(@RequestBody DeleteProductModel deleteProductModel){
        return productService.deleteProduct(deleteProductModel.getDoctorId(), deleteProductModel.getDate());
    }

    @GetMapping("FeignGetProductInfo")
    public ProductVO FeignGetProductInfo(Long id){

        Product doctor = productService.getById(id);
        return BeanCopyUtils.copyBean(doctor, ProductVO.class);
    }
    @GetMapping("/getProductList")
    public ResponseResult getProductList(){
        LambdaQueryWrapper<Product> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Product::getStatus,"1");
        List<Product> list = productService.list(lambdaQueryWrapper);
        return ResponseResult.okResult(list);
    }


    @GetMapping("/getWareByDeptForDays")
    public ResponseResult getWareByDeptForDays(String deptName,long dateSecond){
        return productService.getWareByDeptForDays(deptName,dateSecond);
    }

    @GetMapping("/getProductIdByDoctorAndDateByFeign")
    public List<Long> getProductIdByDoctorAndDate(String userName,Long date){
        return productService.getProductIdByDoctorAndDate(userName,date);
    }

    @GetMapping("/getDoctorsByDateAndArrange")
    public ResponseResult getDoctorsByDateAndArrange(String deptName, Long date){
        return ResponseResult.okResult(productService.getDoctorsByDateAndArrange(deptName,date));
    }





}

