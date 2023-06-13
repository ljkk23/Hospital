package edu.swu.cs.mapper;

import edu.swu.cs.entity.Patient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 就诊人表 Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@Mapper
public interface PatientMapper extends BaseMapper<Patient> {

}
