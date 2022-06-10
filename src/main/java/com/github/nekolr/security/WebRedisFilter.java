package com.github.nekolr.security;

import com.alibaba.fastjson2.JSON;
import com.github.nekolr.pojo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class WebRedisFilter implements WebFilter {

    private static final String TOKEN_HEADER_NAME = "Authorization";

    private String token;


    public WebRedisFilter(@Value("${security.token}") String token) {
        this.token = token;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String original = exchange.getRequest().getHeaders().getFirst(TOKEN_HEADER_NAME);
        if (StringUtils.hasText(original) && token.equals(original)) {
            return chain.filter(exchange);
        }
        return error(exchange);
    }

    private Mono<Void> error(ServerWebExchange exchange) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        DataBuffer bodyDataBuffer = serverHttpResponse
                .bufferFactory()
                .wrap(JSON.toJSONString(Result.fail("no access")).getBytes());
        serverHttpResponse
                .getHeaders()
                .add("Content-Type", "application/json;charset=UTF-8");
        return serverHttpResponse.writeWith(Mono.just(bodyDataBuffer));
    }
}
