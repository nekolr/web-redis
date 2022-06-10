package com.github.nekolr.service;

import com.github.nekolr.pojo.Result;
import com.github.nekolr.util.RedisUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Component
public class RedisService {

    private RedisUtil redisUtil;

    public RedisService(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public Mono<Result> hasKey(String key) {
        if (!StringUtils.hasText(key)) {
            return Mono.just(Result.fail("key is empty"));
        }
        return redisUtil.hasKey(key).map(b -> Result.success(b));
    }

    public Mono<Result> get(String key) {
        if (!StringUtils.hasText(key)) {
            return Mono.just(Result.fail("key is empty"));
        }
        return redisUtil.get(key).map(v -> Result.success(v));
    }

    public Mono<Result> set(String key, Object value) {
        if (!StringUtils.hasText(key)) {
            return Mono.just(Result.fail("key is empty"));
        }
        return redisUtil.set(key, value).map(b -> Result.success(b));
    }

    public Mono<Result> del(String key) {
        if (!StringUtils.hasText(key)) {
            return Mono.just(Result.fail("key is empty"));
        }
        return redisUtil.del(key).map(aLong -> Result.success(aLong == 1));
    }
}
