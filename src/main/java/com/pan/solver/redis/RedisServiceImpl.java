package com.pan.solver.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    private RedisTemplate<String, ?> redisTemplate;

    @Autowired
    public RedisServiceImpl(RedisTemplate<String, ?> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean set(String key, String value) {
        return redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            return true;
        });
    }

    @Override
    public String get(String key) {
        return redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] value = connection.get(serializer.serialize(key));
            return serializer.deserialize(value);
        });
    }

    @Override
    public boolean expire(String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }
}
