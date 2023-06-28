package edu.swu.cs.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import edu.swu.cs.Constants.SystemConstants;
import edu.swu.cs.client.UserClient;
import edu.swu.cs.domain.FeignVO.DoctorFeignVO;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.*;
import edu.swu.cs.entity.VO.OrderVO;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.service.IOrderInfoService;
import org.redisson.Redisson;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;
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
@RequestMapping("/order-info")
public class OrderInfoController {
    @Autowired
    private IOrderInfoService orderInfoService;


    @PostMapping("/createOrder")
    public ResponseResult createOrder(@RequestBody AddOrderModel addOrderModel){
        return orderInfoService.addOrder(addOrderModel.getUserId(), addOrderModel.getPatientId(), addOrderModel.getProductId(), addOrderModel.getType());

    }

    @GetMapping("/cancelOrderById")
    public ResponseResult deleteOrderByID(String orderId){
        return orderInfoService.cancelOrderByOrderId(orderId);
    }

    @PostMapping("/payOrder")
    public ResponseResult payOrder(@RequestBody PayOrderModel payOrderModel){
        return orderInfoService.payOrder(payOrderModel.getOrderId());
    }

    @GetMapping("/getOrderByID")
    public ResponseResult getOrderByID(String orderId){
        OrderVO res=orderInfoService.getOrderByID(orderId);
        if (Objects.isNull(res)){
            return ResponseResult.errorResult(500,"订单为空");
        }
        return ResponseResult.okResult(res);
    }

    @PostMapping("/updateOrderOpinionByID")
    public ResponseResult updateOrderOpinionByID(@RequestBody UpdateOrderOpinionByOrderIdModel updateOrderOpinionByOrderIdModel){
        LambdaUpdateWrapper<OrderInfo> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(OrderInfo::getOrderId,updateOrderOpinionByOrderIdModel.getOrderId())
                .set(OrderInfo::getOpinions,updateOrderOpinionByOrderIdModel.getOpinions())
                .set(OrderInfo::getOrderStatus,2);
        if(!orderInfoService.update(lambdaUpdateWrapper)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATABASE_ERROR);
        }
        return ResponseResult.okResult();
    }

    @GetMapping("/getOrderListByUserId")
    public ResponseResult getOrderListByUserId(Long userId){
        return ResponseResult.okResult(orderInfoService.getOrderListByUserId(userId));
    }

    @GetMapping("/getOrderListByUserIdAndStatus")
    public ResponseResult getOrderListByUserIdAndStatus(Long userId,Integer status){
        return ResponseResult.okResult(orderInfoService.getOrderListByUserIdAndStatus(userId,status));
    }
    @PostMapping("/getOrderListByDoctorId")
    public ResponseResult getOrderListByDoctorId(@RequestBody GetOrderListByDoctorIdModel getOrderListByDoctorIdModel){
        return ResponseResult.okResult(orderInfoService.getOrderListByDoctorId(getOrderListByDoctorIdModel));

    }




}

