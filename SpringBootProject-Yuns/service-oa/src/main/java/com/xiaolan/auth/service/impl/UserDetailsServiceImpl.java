package com.xiaolan.auth.service.impl;

import com.xiaolan.auth.service.SysMenuService;
import com.xiaolan.auth.service.SysUserService;
import com.xiaolan.model.system.SysUser;
import com.xiaolan.security.custom.CustomUser;
import com.xiaolan.security.custom.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username);
        if(null == sysUser) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        if(sysUser.getStatus().intValue() == 0) {
            throw new RuntimeException("账号已停用");
        }
        //通过ID获取权限
        List<String> userButtById = sysMenuService.findUserButtById(sysUser.getId());
        //创建集合 封装SimpleGrantedAuthority
        List<SimpleGrantedAuthority> authority = new ArrayList<>();
        //将用户权限数据封装进List
        for (String parm:userButtById) {
            //存入权限 并消除左右空格
            authority.add(new SimpleGrantedAuthority(parm.trim()));
        }
        return new CustomUser(sysUser, authority);
    }
}
