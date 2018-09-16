package com.pan.solver.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Configuration
@EnableAutoConfiguration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public JedisPoolConfig getRedisConfig() {
        return new JedisPoolConfig();
    }

    @Bean
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setPoolConfig(getRedisConfig());
        log.info("JedisConnectionFactory bean init success.");
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<?, ?> getRedisTemplate() {
        return new StringRedisTemplate(getConnectionFactory());
    }

}
