package com.pan.solver.common.util.rds;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonClientFactory {

    private static RedissonClient redissonClient;

    private RedissonClientFactory() {
    }

    static {
        Config config = new Config();
        config.useSingleServer().setAddress("118.89.39.253:6379");
        redissonClient = Redisson.create(config);
    }

    public static RedissonClient getRedissonClient() {
        return redissonClient;
    }

}
