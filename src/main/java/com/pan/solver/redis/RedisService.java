package com.pan.solver.redis;

public interface RedisService {

    boolean set(final String key, final String value);

    String get(final String key);

    boolean expire(final String key, long expire);

}
