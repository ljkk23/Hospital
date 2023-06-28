package edu.swu.cs.service;

import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.GetOrderListByDoctorIdModel;
import edu.swu.cs.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.swu.cs.entity.VO.GetOrderListByDoctorVO;
import edu.swu.cs.entity.VO.GetOrderListByUserVO;
import edu.swu.cs.entity.VO.GetOrderListPageByDoctorVO;
import edu.swu.cs.entity.VO.OrderVO;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
public interface IOrderInfoService extends IService<OrderInfo> {
    OrderVO getOrderByID(String OrderID);

    ResponseResult cancelOrderByOrderId(String orderId);

    List<GetOrderListByUserVO> getOrderListByUserId(Long userId);

    GetOrderListPageByDoctorVO getOrderListByDoctorId(GetOrderListByDoctorIdModel getOrderListByDoctorIdModel);
    List<GetOrderListByUserVO> getOrderListByUserIdAndStatus(Long userId,Integer status);

    ResponseResult addOrder(Long userID, Long patientID, Long productID,String type);

    ResponseResult payOrder(String orderId);


}
