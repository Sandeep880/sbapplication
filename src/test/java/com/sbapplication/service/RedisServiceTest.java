package com.sbapplication.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testSendMail() {
        redisTemplate.opsForValue().set("email" , "sandeep@email.com");
        Object email = redisTemplate.opsForValue().get("email");
        int a =1;

    }
}