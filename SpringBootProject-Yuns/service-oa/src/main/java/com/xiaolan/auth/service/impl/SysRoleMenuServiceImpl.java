package com.xiaolan.auth.service.impl;

import com.xiaolan.auth.mapper.SysRoleMenuMapper;
import com.xiaolan.auth.service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaolan.model.system.SysRoleMenu;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色菜单 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-23
 */
@Service("sysRoleMenuServiceImpl")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

}
