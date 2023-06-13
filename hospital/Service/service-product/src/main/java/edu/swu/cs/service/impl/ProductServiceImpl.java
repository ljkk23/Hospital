package edu.swu.cs.service.impl;

import edu.swu.cs.entity.Product;
import edu.swu.cs.mapper.ProductMapper;
import edu.swu.cs.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 可预约时间表 服务实现类
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
