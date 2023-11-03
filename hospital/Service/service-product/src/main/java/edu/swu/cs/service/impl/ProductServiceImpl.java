package edu.swu.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import edu.swu.cs.client.DoctorClient;
import edu.swu.cs.client.WareClient;
import edu.swu.cs.domain.FeignVO.GetWareDoctorFeignVO;
import edu.swu.cs.domain.FeignVO.GetWareListByProductIDListVO;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Product;
import edu.swu.cs.entity.VO.GetDoctorsByDateAndArrangeVO;
import edu.swu.cs.entity.VO.GetWareProDuctListVO;
import edu.swu.cs.entity.Ware;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.mapper.ProductMapper;
import edu.swu.cs.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.swu.cs.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 可预约时间表 服务实现类
 * </p>
 *
 * @author liujian
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private DoctorClient doctorClient;

    @Autowired
    private WareClient wareClient;

    @Override
    public ResponseResult getWareByDeptForDays(String deptName, long dateSecond) {
        List<GetWareProDuctListVO> doctors=new ArrayList<>();
        try {
            List<GetWareDoctorFeignVO> doctorListByFeign = doctorClient.getDoctorByDeptByFeign(deptName);
            System.out.println(doctorListByFeign);
            long beforeSecond=dateSecond-86400;
            //将医生的id提取出来方便查询
            List<Long> doctorIDs = doctorListByFeign.stream().map(GetWareDoctorFeignVO::getId).collect(Collectors.toList());
            Map<Long, GetWareDoctorFeignVO> doctorMap = doctorListByFeign.stream().collect(Collectors.toMap(GetWareDoctorFeignVO::getId, Function.identity()));

            //根据医生的id的list查询商品
            LambdaQueryWrapper<Product> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.between(Product::getDate,beforeSecond,dateSecond)
                    .in(Product::getDoctorId,doctorIDs)
                    .eq(Product::getTypeParent,'P');
            List<Product> productList = this.list(lambdaQueryWrapper);

            List<Long> productIDList = productList.stream().map(Product::getId).collect(Collectors.toList());

            //根据商品id获取库存
            List<GetWareListByProductIDListVO> wareList=wareClient.getWareListByProductIdByFeign(productIDList);
            Map<Long, GetWareListByProductIDListVO> wareMap = wareList.stream().collect(Collectors.toMap(GetWareListByProductIDListVO::getProductId, Function.identity()));


            doctors = productList.stream().map(x -> {
                GetWareDoctorFeignVO doctorOne = doctorMap.get(x.getDoctorId());
                GetWareListByProductIDListVO wareOne = wareMap.get(x.getId());

                return new GetWareProDuctListVO(doctorOne.getId(), doctorOne.getTitle(),
                        doctorOne.getRealName(), doctorOne.getAmount()
                        , wareOne.getCount(), doctorOne.getIntroduce(), doctorOne.getSex(),x.getId()
                        , doctorOne.getAvatar());
            }).collect(Collectors.toList());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        return ResponseResult.okResult(doctors);
    }
    @Override
    public ResponseResult saveProductAndWare(Product product) {
        String parentUUid = UUID.randomUUID().toString();
        product.setUuid(parentUUid);
        product.setTypeParent("P");

        if(!this.save(product)){
            return ResponseResult.errorResult(500,"保存父类出错");
        }

        //查询父类的id
        LambdaQueryWrapper<Product> parentWrapper=new LambdaQueryWrapper<>();
        parentWrapper.eq(Product::getUuid,parentUUid);
        Product parentProduct = this.getOne(parentWrapper);


        List<Product> productList = new ArrayList<>();
        //组装该订单的8个由时间段分的子订单
        for (int i = 1; i <= 8; i++) {
            Product tmpProduct = BeanCopyUtils.copyBean(product, Product.class);
            tmpProduct.setParentId(parentProduct.getId());
            tmpProduct.setOffsetTime(i);
            tmpProduct.setUuid(UUID.randomUUID().toString());
            tmpProduct.setTypeParent("s");
            productList.add(tmpProduct);
        }


        if (!this.saveBatch(productList)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATABASE_ERROR);
        }


        //父类库存
        Ware parentWare=new Ware(parentProduct.getId(), 32,0, 0L,0);
        //查询子类的product
        LambdaQueryWrapper<Product> sonWrapper=new LambdaQueryWrapper<>();
        sonWrapper.eq(Product::getParentId,parentProduct.getId());
        List<Product> sonProductList = this.list(sonWrapper);

        List<Ware> wareList = sonProductList.stream().map(x -> {
            return new Ware(x.getId(), 4, 0, parentProduct.getId(), x.getOffsetTime());
        }).collect(Collectors.toList());
        wareList.add(parentWare);

        //生成库存
        Boolean success = wareClient.addWareListByFeign(wareList);
        if (!success) {
            return ResponseResult.errorResult(500,"增加库岑失败");
        }
        return ResponseResult.okResult();
    }

    @Override
    public List<Long> getProductIdByDoctorAndDate(String userName, Long date) {
        Long beforeSecond=date-86400;

        Long doctorId = doctorClient.getDoctorIdByNameFeign(userName);

        LambdaQueryWrapper<Product> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Product::getDoctorId,doctorId)
                .eq(Product::getTypeParent,"s")
                .between(Product::getDate,beforeSecond,date);
        List<Product> list = this.list(lambdaQueryWrapper);


        return list.stream().map(Product::getId).collect(Collectors.toList());
    }

    @Override
    public List<GetDoctorsByDateAndArrangeVO> getDoctorsByDateAndArrange(String deptName, Long date) {
        //查询该部门下的所有医生
        List<GetWareDoctorFeignVO> doctorByDeptByFeign = doctorClient.getDoctorByDeptByFeign(deptName);
        List<Long> doctorIds = doctorByDeptByFeign.stream().map(GetWareDoctorFeignVO::getId).collect(Collectors.toList());
        //查询已经安排的医生id
        LambdaQueryWrapper<Product> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Product::getDate,date)
                .in(Product::getDoctorId,doctorIds);
        List<Product> arrangeDoc = this.list(lambdaQueryWrapper);

        List<Long> arrangeDoctorId = arrangeDoc.stream().map(Product::getDoctorId).distinct().collect(Collectors.toList());
        //将所有医生拷贝为VO
        List<GetDoctorsByDateAndArrangeVO> getDoctorsByDateAndArrangeVOS = BeanCopyUtils.copyBeanList(doctorByDeptByFeign, GetDoctorsByDateAndArrangeVO.class);
        getDoctorsByDateAndArrangeVOS.forEach(x->{
            if (arrangeDoctorId.contains(x.getId())){
                x.setIsArrange(1);
            }else {
                x.setIsArrange(0);
            }
        });
        return getDoctorsByDateAndArrangeVOS;


    }

    @Override
    public ResponseResult deleteProduct(String doctorId, Long date) {
        LambdaQueryWrapper<Product> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Product::getDoctorId,doctorId)
                .eq(Product::getDate,date);

        if (this.baseMapper.delete(lambdaQueryWrapper)==0){
            return ResponseResult.errorResult(500,"删除失败：数据不存在或者参数有问题");
        }
        return ResponseResult.okResult();
    }

}
