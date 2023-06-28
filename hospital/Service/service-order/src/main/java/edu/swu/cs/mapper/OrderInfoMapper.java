package edu.swu.cs.mapper;

import edu.swu.cs.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

}
