package com.xiaolan.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaolan.model.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

}
