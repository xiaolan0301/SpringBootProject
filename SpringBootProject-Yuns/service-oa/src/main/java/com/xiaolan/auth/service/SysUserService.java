package com.xiaolan.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaolan.model.system.SysUser;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-22
 */

public interface SysUserService extends IService<SysUser> {
    void updateStatus(Long id, Integer status);
    void SaveUser(SysUser sysUser);
    SysUser getByUsername(String username);
}
