package edu.swu.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.VO.GetWareByTimeVO;
import edu.swu.cs.entity.Ware;
import edu.swu.cs.mapper.WareMapper;
import edu.swu.cs.service.IWareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    @Override
    public Boolean lockWare(Long productID) {
        //子产品
        LambdaQueryWrapper<Ware> lambdaQueryWrapper=new LambdaQueryWrapper<Ware>();
        lambdaQueryWrapper.eq(Ware::getProductId,productID);
        Ware ware = this.getOne(lambdaQueryWrapper);

        LambdaQueryWrapper<Ware> lambdaQueryWrapperParent=new LambdaQueryWrapper<>();
        lambdaQueryWrapperParent.eq(Ware::getProductId,ware.getParentProductId());
        Ware wareParent = this.getOne(lambdaQueryWrapperParent);


        if (ware.getAmount()- ware.getLockAmount()>0){
            ware.setLockAmount(ware.getLockAmount()+1);
            this.updateById(ware);
            //更新父产品的库存
            LambdaUpdateWrapper<Ware> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Ware::getProductId,ware.getParentProductId())
                    .set(Ware::getLockAmount,wareParent.getLockAmount()+1);
            this.update(lambdaUpdateWrapper);
            return true;
        }else {
            return false;
        }

    }




    @Override
    public ResponseResult getDoctorProductByTime(long productId) {

        LambdaQueryWrapper<Ware> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Ware::getParentProductId,productId);

        List<Ware> wareList = this.list(lambdaQueryWrapper);
        List<GetWareByTimeVO> wareByTimeVOS = wareList.stream().map(x -> new GetWareByTimeVO(x.getOffsetTime(), x.getAmount() - x.getLockAmount(), x.getProductId()))
                .collect(Collectors.toList());
        return ResponseResult.okResult(wareByTimeVOS);

    }


}
