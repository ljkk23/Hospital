package edu.swu.cs.service;

import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.DeptCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.swu.cs.entity.VO.DeptCategoryVO;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 科室分类表 服务类
 * </p>
 *
 * @author liujian
 * @since 2022-11-13
 */
public interface IDeptCategoryService extends IService<DeptCategory> {

    List<DeptCategoryVO> getCategory();
    List<DeptCategoryVO> getCatalogJsonFromDB();

    List<DeptCategory> getParentCid(List<DeptCategory> selectList, Long parentCid);

    ResponseResult deleteCategory(Long id);

    ResponseResult addDept(DeptCategory deptCategory);

    List<String> searchDept(String deptName);
}
