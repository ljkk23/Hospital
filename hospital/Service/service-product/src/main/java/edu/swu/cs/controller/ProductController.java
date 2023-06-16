package edu.swu.cs.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.swu.cs.domain.FeignVO.ProductVO;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Product;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.service.IProductService;
import edu.swu.cs.utils.BeanCopyUtils;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/addProduct")
    public ResponseResult addProduct(Product product){
        if (!productService.save(product)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"插入数据库出错");
        }
        return ResponseResult.okResult();
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
    public ResponseResult getWareByDeptForDays(String deptName,Integer dateIndex){
          return null;
    }





}

