package edu.swu.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.swu.cs.domain.securityEntity.Role;

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
public interface RoleMapper extends BaseMapper<Role> {

    List<String> getRoleByUserId(Long userID);

}
