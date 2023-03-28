package com.xiaolan.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaolan.model.system.SysRole;
import com.xiaolan.vo.system.AssginRoleVo;

import java.util.List;
import java.util.Map;

public interface RoleService extends IService<SysRole> {
    List<SysRole> findRoleByAdminId(Long userId);
    public void doAssign(AssginRoleVo assginRoleVo);
}
