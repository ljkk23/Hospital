package edu.swu.cs.service;

import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.swu.cs.entity.VO.GetDoctorsByDateAndArrangeVO;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * <p>
 * 可预约时间表 服务类
 * </p>
 *
 * @author liujian
 */
public interface IProductService extends IService<Product> {

    ResponseResult getWareByDeptForDays(String deptName,long dateSecond);

    ResponseResult saveProductAndWare(Product product);

    List<Long> getProductIdByDoctorAndDate(String userName,Long date);

    List<GetDoctorsByDateAndArrangeVO> getDoctorsByDateAndArrange(String deptName, Long date);

    ResponseResult deleteProduct(String doctorId,Long date);
}
