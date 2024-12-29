package com.sbapplication.service;

public interface RedisService {

    public void set(String key, Object o, Long ttl);

    public <T> T get(String key, Class<T> entityClass);
}

