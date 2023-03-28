package com.xiaolan.auth.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaolan.auth.mapper.SysUserMapper;
import com.xiaolan.auth.service.SysUserService;
import com.xiaolan.common.util.MD5;
import com.xiaolan.model.system.SysUser;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-22
 */
@Service("SysUserServiceImpl")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    //更改用户状态 ，当点击按钮 1变0 0变1
    @Override
    public void updateStatus(Long id, Integer status) {
        SysUser sysUser = baseMapper.selectById(id);
        if (status!=0 && status!=1){
            return;
        }
        sysUser.setStatus(status);
        baseMapper.updateById(sysUser);
    }

    @Override
    public void SaveUser(SysUser sysUser) {
        String password = sysUser.getPassword();
        String encrypt = MD5.encrypt(password);
        sysUser.setPassword(encrypt);
        System.out.println(sysUser);
        baseMapper.insert(sysUser);
    }

    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        SysUser sysUser = baseMapper.selectOne(wrapper);

        return sysUser;
    }
}
