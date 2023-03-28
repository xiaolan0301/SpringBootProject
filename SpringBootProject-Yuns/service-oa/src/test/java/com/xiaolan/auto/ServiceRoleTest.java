package com.xiaolan.auto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaolan.auth.service.RoleService;
import com.xiaolan.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.util.List;
@SpringBootTest
@MapperScan("com.xiaolan.auto.mapper")
public class ServiceRoleTest {
    @Autowired
    private RoleService roleService;

    @Test
    public void ServiceTest(){
        List<SysRole> list = roleService.list();
        System.out.println(list);

    }

    @Test
    public void testTiaojian(){

        Page<SysRole> pageParam = new Page<>(1,3);

        //2 封装条件，判断条件是否为空，不为空进行封装
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        String roleName = null;
        if(!StringUtils.isEmpty(roleName)) {
            //封装 like模糊查询
            wrapper.like(SysRole::getRoleName,roleName);
        }

        //3 调用方法实现
        IPage<SysRole> pageModel = roleService.page(pageParam, wrapper);
    }
}
