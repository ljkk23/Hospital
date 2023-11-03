package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2023-10-27
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
