package com.xiaolan.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaolan.model.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2023-03-22
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
