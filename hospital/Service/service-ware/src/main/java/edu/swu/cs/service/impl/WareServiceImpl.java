package edu.swu.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sun.xml.internal.ws.server.provider.ProviderInvokerTube;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Ware;
import edu.swu.cs.mapper.WareMapper;
import edu.swu.cs.service.IWareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单的库存 服务实现类
 * </p>
 *
 * @author liujian
 * @since 2022-11-22
 */
@Service
public class WareServiceImpl extends ServiceImpl<WareMapper, Ware> implements IWareService {

    @Override
    public Boolean lockWare(Long productID) {
        LambdaQueryWrapper<Ware> lambdaQueryWrapper=new LambdaQueryWrapper<Ware>();
        lambdaQueryWrapper.eq(Ware::getProductId,productID);
        Ware ware = this.getOne(lambdaQueryWrapper);
        if (ware.getAmount()- ware.getLockAmount()>0){
            ware.setLockAmount(ware.getLockAmount()+1);
            this.updateById(ware);
            return true;
        }else {
            return false;
        }

    }

    @Override
    public ResponseResult getWareByDeptForDays(String deptName) {
        return null;
    }
}
