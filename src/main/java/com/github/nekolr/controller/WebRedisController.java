package com.github.nekolr.controller;

import com.github.nekolr.pojo.Result;
import com.github.nekolr.pojo.SetValueCmd;
import com.github.nekolr.service.RedisService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("dress")
public class WebRedisController {

    private RedisService redisService;

    public WebRedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/hasKey/{key}")
    public Mono<Result> hasKey(@PathVariable String key) {
        return redisService.hasKey(key);
    }

    @GetMapping("/{key}")
    public Mono<Result> getValue(@PathVariable String key) {
        return redisService.get(key);
    }

    @PostMapping("/{key}")
    public Mono<Result> setValue(@PathVariable String key, @RequestBody SetValueCmd cmd) {
        return redisService.set(key, cmd.value());
    }

    @DeleteMapping("/{key}")
    public Mono<Result> deleteKey(@PathVariable String key) {
        return redisService.del(key);
    }
}
