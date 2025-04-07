package com.a702.finafanbe.global.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
@Profile("redis-sentinel")
public class RedisConfig {

    @Value("${spring.data.redis.host:localhost}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private int port;

    @Value("${spring.data.redis.password:}")
    private String password;

    @Value("${spring.data.redis.sentinel.master:mymaster}")
    private String sentinelMaster;

    @Value("${spring.data.redis.sentinel.nodes:}")
    private String sentinelNodes;

    @Bean
    @Profile("!redis-sentinel")
    public RedisConnectionFactory standaloneRedisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(host, port);
        if (password != null && !password.isEmpty()) {
            redisConfig.setPassword(password);
        }
        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    @Profile("redis-sentinel")
    public RedisConnectionFactory sentinelRedisConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
            .master(sentinelMaster);

        Set<String> sentinels = new HashSet<>();
        if (sentinelNodes != null && !sentinelNodes.isEmpty()) {
            sentinels.addAll(Arrays.asList(sentinelNodes.split(",")));
        }

        for (String sentinel : sentinels) {
            String[] parts = sentinel.split(":");
            sentinelConfig.sentinel(parts[0], Integer.parseInt(parts[1]));
        }

        if (password != null && !password.isEmpty()) {
            sentinelConfig.setPassword(RedisPassword.of(password));
        }

        return new LettuceConnectionFactory(sentinelConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }
}