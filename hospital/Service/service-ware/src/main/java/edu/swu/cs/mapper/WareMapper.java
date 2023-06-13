package edu.swu.cs.mapper;

import edu.swu.cs.entity.Ware;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单的库存 Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2022-11-22
 */
@Mapper
public interface WareMapper extends BaseMapper<Ware> {

}
