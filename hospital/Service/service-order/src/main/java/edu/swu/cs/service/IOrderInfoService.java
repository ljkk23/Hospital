package edu.swu.cs.service;

import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
public interface IOrderInfoService extends IService<OrderInfo> {
    ResponseResult getOrderByID(Long OrderID);

    ResponseResult addOrder(Long userID, Long patientID, Long productID,String type);
}
