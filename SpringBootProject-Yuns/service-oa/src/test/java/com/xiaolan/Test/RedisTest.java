package com.xiaolan.Test;


import com.xiaolan.process.service.OaProcessTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private OaProcessTypeService typeService;

    @Test
    public void testRedis(){
        stringRedisTemplate.opsForValue().set("xiaocai", "888");
        String res = stringRedisTemplate.opsForValue().get("xiaocai");
        System.out.println(res);


    }

    @Test
    public void testp(){
        System.out.println(typeService.findProcessType()+"11111111111111");

    }
}
