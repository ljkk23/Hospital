package edu.swu.cs.mapper;

import edu.swu.cs.entity.DoctorLeave;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 医生的请假表 Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2023-06-21
 */
@Mapper
public interface DoctorLeaveMapper extends BaseMapper<DoctorLeave> {

}
