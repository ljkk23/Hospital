package edu.swu.cs.controller;


import edu.swu.cs.Constants.SystemConstants;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.service.IOrderInfoService;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@RestController
@RequestMapping("/hotOrder-info")
public class HotOrderInfoController {
    @Autowired
    private IOrderInfoService orderInfoService;


    @GetMapping("/createOrder")
    public ResponseResult createOrder(Long userID,Long patientID,Long productID,String type){
        return orderInfoService.addHotOrder(userID,patientID,productID,type);

    }

}

