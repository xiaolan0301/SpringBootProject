package com.xiaolan.auto;

import com.xiaolan.auth.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@MapperScan("com.xiaolan.auto.mapper")
public class SysRoleTest {
    @Autowired
    SysRoleMapper mapper;

    @Test
    void testRole(){
        List list = mapper.selectList(null);
        System.out.println(list);

    }

    @Test
    void testDelect(){
        int i = mapper.deleteById(9);



    }
}
