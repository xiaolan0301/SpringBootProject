package com.xiaolan.process.mapper;

import com.xiaolan.model.process.ProcessRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 审批记录 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2023-03-27
 */
@Mapper
public interface OaProcessRecordMapper extends BaseMapper<ProcessRecord> {

}
