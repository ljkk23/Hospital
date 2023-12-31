package edu.swu.cs.service;

import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Ware;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单的库存 服务类
 * </p>
 *
 * @author liujian
 * @since 2022-11-22
 */
public interface IWareService extends IService<Ware> {

    Boolean lockWare(Long productID);


    ResponseResult getDoctorProductByTime(long productId);



}
