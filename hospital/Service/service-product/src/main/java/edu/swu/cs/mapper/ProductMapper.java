package edu.swu.cs.mapper;

import edu.swu.cs.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 可预约时间表 Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}
