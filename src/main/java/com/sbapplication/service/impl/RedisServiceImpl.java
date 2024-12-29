package com.sbapplication.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbapplication.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

class RedisDataNotFoundException extends RuntimeException {
    public RedisDataNotFoundException(String message) {
        super(message);
    }
}

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass) {
        try {
            Object o = redisTemplate.opsForValue().get(key);
            if(o == null)
            {
                String errorMessage = "There is no data in Redis Database for key: " + key;
                log.error(errorMessage);
                throw new RedisDataNotFoundException(errorMessage);
            }
            else {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(o.toString(), entityClass);
            }
        } catch (Exception e) {
            String errorMessage = "Exception while converting value for key: " + key;
            log.error(errorMessage, e);
            return null;
        }
    }

    public void set(String key, Object o, Long ttl) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception ", e);
        }
    }
}
