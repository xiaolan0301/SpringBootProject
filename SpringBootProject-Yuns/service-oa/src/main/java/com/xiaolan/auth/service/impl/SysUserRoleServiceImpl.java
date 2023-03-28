package com.xiaolan.auth.service.impl;

import com.xiaolan.auth.mapper.SysUserRoleMapper;
import com.xiaolan.auth.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaolan.model.system.SysUserRole;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-22
 */
@Service("sysUserRoleServiceImpl")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
