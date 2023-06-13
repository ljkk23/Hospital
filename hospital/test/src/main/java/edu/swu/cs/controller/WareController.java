package edu.swu.cs.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单的库存 前端控制器
 * </p>
 *
 * @author liujian
 * @since 2022-11-22
 */
@RestController
@RequestMapping("/test")
public class WareController {

    @GetMapping("test")
    public Integer addStock( ){
        return 1;
    }






}

