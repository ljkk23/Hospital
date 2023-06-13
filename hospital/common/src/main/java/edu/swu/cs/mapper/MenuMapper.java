package edu.swu.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.swu.cs.domain.securityEntity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2022-11-16
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectRoutersByUserId(Long userId);

    List<Menu> selectAllRouters();
}
