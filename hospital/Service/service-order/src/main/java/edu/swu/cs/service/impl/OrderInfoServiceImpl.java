package edu.swu.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.swu.cs.Constants.SystemConstants;
import edu.swu.cs.client.ProductClient;
import edu.swu.cs.client.UserClient;
import edu.swu.cs.client.WareClient;
import edu.swu.cs.domain.FeignVO.DoctorFeignVO;
import edu.swu.cs.domain.FeignVO.PatientVo;
import edu.swu.cs.domain.FeignVO.ProductVO;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.GetOrderListByDoctorIdModel;
import edu.swu.cs.entity.OrderInfo;
import edu.swu.cs.entity.VO.*;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.mapper.OrderInfoMapper;
import edu.swu.cs.service.IOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.swu.cs.utils.BeanCopyUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author liujian
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductClient productClient;
    private static final int CORE_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 60L;

    @Autowired
    private WareClient wareClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public OrderVO getOrderByID(String orderId) {
        long startTime=System.currentTimeMillis(); //获取开始时间
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());

        OrderVO orderVO=new OrderVO();
        //要测的程序或方法


        //通过openfeign远程调用product中的通过id获取product的信息，以及patientID获取patient的信息，组装成订单信息返回给前端

        LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderInfo::getOrderId,orderId);
        OrderInfo orderInfo = this.getOne(lambdaQueryWrapper);


        orderVO.setOrderId(orderInfo.getOrderId())
                .setCreateTime(orderInfo.getCreateTime())
                .setOrderStatus(orderInfo.getOrderStatus())
                .setOpinions(orderInfo.getOpinions());
//        ProductVO productVO = productClient.FeignGetProductInfo(orderInfo.getProductId());
//        orderVO.setDate(productVO.getDate());

        CompletableFuture<ProductVO> productFuture = CompletableFuture.supplyAsync(() -> {
            //1.获取product的信息
            ProductVO productVO = productClient.FeignGetProductInfo(orderInfo.getProductId());

            orderVO.setDate(productVO.getDate())
                    .setOffsetTime(productVO.getOffsetTime());
            return productVO;

        }, executor);
        //ProductVO productVO = productClient.FeignGetProductInfo(orderInfo.getProductId());
        CompletableFuture<Void> doctorFuture = productFuture.thenAcceptAsync((res) -> {
            //根据product中的医生id查询医生的信息
            DoctorFeignVO doctorFeignVO = userClient.FeignGetDoctorInfo(res.getDoctorId());
            orderVO.setDeptId(doctorFeignVO.getDeptId())
                    .setDoctorAvatar(doctorFeignVO.getAvatar())
                    .setDoctorEmail(doctorFeignVO.getEmail())
                    .setDoctorPhonenumber(doctorFeignVO.getPhonenumber())
                    .setDoctorSex(doctorFeignVO.getSex())
                    .setTitle(doctorFeignVO.getTitle())
                    .setDoctorRealName(doctorFeignVO.getRealName())
                    .setDeptName(doctorFeignVO.getDeptName())
                    .setAmount(doctorFeignVO.getAmount());
        }, executor);

//        DoctorFeignVO doctorFeignVO = userClient.FeignGetDoctorInfo(productVO.getDoctorId());
//        orderVO.setDeptId(doctorFeignVO.getDeptId())
//                .setDoctorAvatar(doctorFeignVO.getAvatar())
//                .setDoctorEmail(doctorFeignVO.getEmail())
//                .setDoctorPhonenumber(doctorFeignVO.getPhonenumber())
//                .setDoctorSex(doctorFeignVO.getSex())
//                .setTitle(doctorFeignVO.getTitle())
//                .setDoctorUserName(doctorFeignVO.getUserName())
//                .setAmount(doctorFeignVO.getAmount());

        CompletableFuture<Void> patientFuture = CompletableFuture.runAsync(() -> {
            //1.获取patient的信息
            PatientVo patientVo = userClient.FeignGetPatientInfo(orderInfo.getPatientId());
            orderVO.setPatientAge(patientVo.getAge())
                    .setPatientCardId(patientVo.getCardId())
                    .setPatientSex(patientVo.getSex())
                    .setPatientPhonenumber(patientVo.getPhonenumber())
                    .setPatientUserName(patientVo.getUserName())
                    .setMedicalHistory(patientVo.getMedicalHistory());

        }, executor);

        PatientVo patientVo = userClient.FeignGetPatientInfo(orderInfo.getPatientId());
        orderVO.setPatientAge(patientVo.getAge())
                .setPatientCardId(patientVo.getCardId())
                .setPatientSex(patientVo.getSex())
                .setPatientPhonenumber(patientVo.getPhonenumber())
                .setPatientUserName(patientVo.getUserName());

        //等待所有任务都完成
        try {
            CompletableFuture.allOf(doctorFuture,patientFuture,productFuture).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        long endTime=System.currentTimeMillis(); //获取结束时间

        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

        return orderVO;
    }

    @Override
    public ResponseResult cancelOrderByOrderId(String orderId) {
        LambdaUpdateWrapper<OrderInfo> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(OrderInfo::getOrderId,orderId)
                .set(OrderInfo::getOrderStatus,3);
        if(!this.update(lambdaUpdateWrapper)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATABASE_ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public List<GetOrderListByUserVO> getOrderListByUserId(Long userId) {
        //查询该userId的所有order的id
        LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderInfo::getUserId,userId);
        List<OrderInfo> orderInfos = this.list(lambdaQueryWrapper);

        List<OrderVO> orderVOS = orderInfos.stream().map(x -> {
            return getOrderByID(x.getOrderId());
        }).collect(Collectors.toList());

        List<GetOrderListByUserVO> getOrderListByUserVOS = BeanCopyUtils.copyBeanList(orderVOS, GetOrderListByUserVO.class);

        return getOrderListByUserVOS;
    }


    @Override
    public GetOrderListPageByDoctorVO getOrderListByDoctorId(GetOrderListByDoctorIdModel getOrderListByDoctorIdModel) {
        //查询该userId的所有order的id

        List<Long> productIdList = productClient.getProductIdByDoctorAndDate(getOrderListByDoctorIdModel.getUserName(), getOrderListByDoctorIdModel.getDate());

        Page<OrderInfo> page=new Page<>(getOrderListByDoctorIdModel.getPageNum(), getOrderListByDoctorIdModel.getPageSize());
        LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(OrderInfo::getProductId,productIdList)
                .eq(OrderInfo::getOrderStatus,1);

        Page<OrderInfo> orderInfoPage = this.baseMapper.selectPage(page, lambdaQueryWrapper);

        List<OrderInfo> orderInfos = orderInfoPage.getRecords();
        List<OrderVO> orderVOS = orderInfos.stream().map(x -> {
            return getOrderByID(x.getOrderId());
        }).collect(Collectors.toList());

        List<OrderVO> orderSortedVOS = orderVOS.stream().sorted(Comparator.comparing(OrderVO::getOffsetTime)).collect(Collectors.toList());

        List<GetOrderListByDoctorVO> getOrderListByDoctorVOS = BeanCopyUtils.copyBeanList(orderSortedVOS, GetOrderListByDoctorVO.class);

        GetOrderListPageByDoctorVO getOrderListPageByDoctorVO=new GetOrderListPageByDoctorVO(orderInfoPage.getSize(),orderInfoPage.getCurrent()
                ,Math.ceil((double) orderInfoPage.getTotal()/orderInfoPage.getSize()),getOrderListByDoctorVOS);

        return getOrderListPageByDoctorVO;
    }

    @Override
    public List<GetOrderListByUserVO> getOrderListByUserIdAndStatus(Long userId, Integer status) {
        List<GetOrderListByUserVO> orderListByUserId = this.getOrderListByUserId(userId);
        return orderListByUserId.stream().filter(x -> x.getOrderStatus() == status).collect(Collectors.toList());

    }

    @Transactional
    @Override
    public ResponseResult addOrder(Long userID, Long patientID, Long productID,String type) {
        //60秒内提交的订单视为幂等
        String redisKey= "AddOrder:"+userID+":"+ patientID + ":"+productID;
        Object orderRedis = redisTemplate.opsForValue().get(redisKey);
        if (Objects.isNull(orderRedis)){
            redisTemplate.opsForValue().set(redisKey,redisKey,60,TimeUnit.SECONDS);
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.IDEMPOTENT_ERROR);
        }
        //业务逻辑
        String orderID = UUID.randomUUID().toString();
        OrderInfo orderInfo=new OrderInfo(productID,userID,patientID,userID,orderID,type);
        if (!this.save(orderInfo)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"插入数据库出错");
        }
        rabbitTemplate.convertAndSend(SystemConstants.ORDER_EXCHANGE,"order.locked",orderInfo,new CorrelationData(UUID.randomUUID().toString()));
         //锁库存
        Boolean lockResult = wareClient.lockWare(productID);
        if (!lockResult){
            throw new RuntimeException("库存不够");
        }
        //模仿库存锁定之后，本地事务的回滚
//        int a=10/0;

        return ResponseResult.okResult(new AddOrderVO(orderInfo.getOrderId()));
    }

    @Override
    public ResponseResult payOrder(String orderId) {
        //判断订单是否可支付
        LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderInfo::getOrderId,orderId);
        OrderInfo oneOrder = this.getOne(lambdaQueryWrapper);
        if (oneOrder.getOrderStatus()!=0){
            return ResponseResult.errorResult(500,"订单已经取消");
        }

        LambdaUpdateWrapper<OrderInfo> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(OrderInfo::getOrderId,orderId)
                .set(OrderInfo::getOrderStatus,1);
        if (!this.update(lambdaUpdateWrapper)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATABASE_ERROR);
        }
        return ResponseResult.okResult();
    }
}
