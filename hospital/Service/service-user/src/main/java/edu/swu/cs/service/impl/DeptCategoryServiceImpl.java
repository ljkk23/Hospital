package edu.swu.cs.service.impl;

import com.alibaba.fastjson.JSON;
import edu.swu.cs.Exception.SystemException;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.DeptCategory;
import edu.swu.cs.entity.VO.DeptCategoryVO;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.mapper.DeptCategoryMapper;
import edu.swu.cs.service.IDeptCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.swu.cs.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 科室分类表 服务实现类
 * </p>
 *
 * @author liujian
 * @since 2022-11-13
 */
@Service
public class DeptCategoryServiceImpl extends ServiceImpl<DeptCategoryMapper, DeptCategory> implements IDeptCategoryService {

    @Autowired
    public RedisTemplate redisTemplate;

    @Cacheable(value = {"category"}, key = "#root.method.name")
    @Override
    public List<DeptCategoryVO> getCategory() {
//        // 1.从缓存中读取分类信息
//        Object categoryJson = redisTemplate.opsForValue().get("categoryJson");
//        if (StringUtils.isEmpty(categoryJson)) {
            // 2. 缓存中没有，查询数据库
            List<DeptCategoryVO> catalogJsonFromDB = getCatalogJsonFromDB();
            // 3. 查询到的数据存放到缓存中，将对象转成 JSON 存储
           // redisTemplate.opsForValue().set("categoryJson", JSON.toJSONString(catalogJsonFromDB));
            return catalogJsonFromDB;
//        }
//        return ResponseResult.okResult(categoryJson);
    }

    @Override
    public List<DeptCategoryVO> getCatalogJsonFromDB() {
        //一次查询所有的category
        List<DeptCategory> allCategory = list();

        //筛选出一级分类
        List<DeptCategory> leve1Category = getParentCid(allCategory, -1L);
        List<DeptCategoryVO> leve1CategoryVO = BeanCopyUtils.copyBeanList(leve1Category, DeptCategoryVO.class);


        List<DeptCategoryVO> deptCategoryVOList=leve1CategoryVO.stream().peek(l->{
            //查询二级分类
            List<DeptCategory> leve2Category = getParentCid(allCategory, l.getId());
            List<DeptCategoryVO>  leve2CategoryVO = BeanCopyUtils.copyBeanList(leve2Category, DeptCategoryVO.class);
            //给二级分类查设置三级分类
            List<DeptCategoryVO> collect = leve2CategoryVO.stream().peek(l2 -> {
                List<DeptCategoryVO> leve3CategoryVO = BeanCopyUtils.copyBeanList(getParentCid(allCategory, l2.getId()), DeptCategoryVO.class);
                l2.setChildCategory(leve3CategoryVO);
            }).collect(Collectors.toList());
            //给一极分类设置二级分类
            l.setChildCategory(leve2CategoryVO);
        }).collect(Collectors.toList());

        return deptCategoryVOList;
    }

    @Override
    public List<DeptCategory> getParentCid(List<DeptCategory> selectList, Long parentCid) {
        return selectList.stream().filter(item -> item.getParentId().equals(parentCid)).collect(Collectors.toList());
    }
    @CacheEvict(value = "category", key = "'getCategory'")
    @Override
    public ResponseResult deleteCategory(Long id) {
        this.removeById(id);

        return ResponseResult.okResult();
    }
    //新增dept就需要更改category
    @CacheEvict(value = "category")
    @Override
    public ResponseResult addDept(DeptCategory deptCategory) {
        try {
            this.save(deptCategory);
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.DATABASE_ERROR);
        }
        return ResponseResult.okResult();
    }


}
