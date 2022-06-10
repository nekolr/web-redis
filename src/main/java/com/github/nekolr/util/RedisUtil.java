package com.github.nekolr.util;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class RedisUtil {

    private ReactiveRedisTemplate<String, Object> redisTemplate;

    public RedisUtil(ReactiveRedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Mono<Boolean> hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Mono<Boolean> expire(String key, long seconds) {
        if (seconds <= 0) {
            return Mono.just(false);
        }
        return redisTemplate.expire(key, Duration.ofSeconds(seconds));
    }

    public Mono<Object> get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Mono<Boolean> set(String key, Object value) {
        return redisTemplate.opsForValue().set(key, value);
    }

    public Mono<Long> del(String... keys) {
        if (keys == null || keys.length == 0) {
            return Mono.just(0L);
        }

        if (keys.length == 1) {
            return redisTemplate.delete(keys[0]);
        } else {
            return redisTemplate.delete(keys);
        }
    }


}
