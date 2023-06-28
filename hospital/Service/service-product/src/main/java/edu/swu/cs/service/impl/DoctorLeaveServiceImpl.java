package edu.swu.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.swu.cs.client.DoctorClient;
import edu.swu.cs.entity.ApprovalLeaveModel;
import edu.swu.cs.entity.DoctorLeave;
import edu.swu.cs.entity.Product;
import edu.swu.cs.entity.VO.GetLeaveListPageVO;
import edu.swu.cs.mapper.DoctorLeaveMapper;
import edu.swu.cs.service.IDoctorLeaveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.swu.cs.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 医生的请假表 服务实现类
 * </p>
 *
 * @author liujian
 * @since 2023-06-21
 */
@Service
public class DoctorLeaveServiceImpl extends ServiceImpl<DoctorLeaveMapper, DoctorLeave> implements IDoctorLeaveService {

    @Autowired
    private IProductService productService;

    @Autowired
    private DoctorClient doctorClient;

    @Override
    public GetLeaveListPageVO getLeaveAll(Integer pageSize,Integer pageNum) {

        Page<DoctorLeave> page=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<DoctorLeave> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(DoctorLeave::getId);
        Page<DoctorLeave> doctorLeavePage = this.baseMapper.selectPage(page, lambdaQueryWrapper);

        List<DoctorLeave> records = doctorLeavePage.getRecords();

        GetLeaveListPageVO getLeaveListPageVO=new GetLeaveListPageVO(records,doctorLeavePage.getSize(),doctorLeavePage.getCurrent()
                , (long) (Math.ceil(doctorLeavePage.getTotal())/ doctorLeavePage.getSize()));
        return getLeaveListPageVO;

    }
    @Transactional
    @Override
    public Boolean approveLeave(ApprovalLeaveModel approvalLeaveModel) {
        //更新请假单状态
        LambdaUpdateWrapper<DoctorLeave> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(DoctorLeave::getId,approvalLeaveModel.getId())
                .set(DoctorLeave::getState,approvalLeaveModel.getState())
                .set(DoctorLeave::getApprovaler,approvalLeaveModel.getApprovaler());
        if (!this.update(lambdaUpdateWrapper)){
            throw new RuntimeException("更改请假单状态出错");
        }

        //根据请假单id查询时间
        DoctorLeave doctorLeave = this.getById(approvalLeaveModel.getId());

        String doctorLeaveUserName = doctorLeave.getUserName();
        Long startTime = doctorLeave.getStartTime()/1000;
        Long endTime = doctorLeave.getEndTime()/1000;

        //根据doctorName查询id
        Long doctorId = doctorClient.getDoctorIdByNameFeign(doctorLeaveUserName);
        Long changeUserId = doctorClient.getDoctorIdByNameFeign(doctorLeave.getChangeuser());

        LambdaQueryWrapper<Product> productLambdaQueryWrapper=new LambdaQueryWrapper<>();
        productLambdaQueryWrapper.between(Product::getDate,startTime,endTime);
        Integer productCount = productService.getBaseMapper().selectCount(productLambdaQueryWrapper);
        if (productCount==0){
            return true;
        }

        LambdaUpdateWrapper<Product> productLambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        productLambdaUpdateWrapper.eq(Product::getDoctorId,doctorId)
                .between(Product::getDate,startTime,endTime)
                        .set(Product::getDoctorId,changeUserId);
        //更新商品对应的人
        if (!productService.update(productLambdaUpdateWrapper)){
            throw new RuntimeException("更新换班人错误");
        }
        return true;


    }

    @Override
    public GetLeaveListPageVO getAllLeaveByDoctor(String userName, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<DoctorLeave> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DoctorLeave::getUserName,userName);
        Page<DoctorLeave> page=new Page<>(pageNum,pageSize);
        Page<DoctorLeave> doctorLeavePage = this.baseMapper.selectPage(page, lambdaQueryWrapper);

        List<DoctorLeave> records = doctorLeavePage.getRecords();

        GetLeaveListPageVO getLeaveListPageVO=new GetLeaveListPageVO(records,doctorLeavePage.getSize(),doctorLeavePage.getCurrent()
                , (long) (Math.ceil(doctorLeavePage.getTotal())/ doctorLeavePage.getSize()));
        return getLeaveListPageVO;
    }
}
