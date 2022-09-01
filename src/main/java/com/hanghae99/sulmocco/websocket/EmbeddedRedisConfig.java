package com.hanghae99.sulmocco.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 로컬 환경일경우 내장 레디스가 실행된다.
 */
@Profile("local")
@Configuration
@RequiredArgsConstructor
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() {
//        redisServer = new RedisServer(redisPort);
//        redisServer.start();
        redisServer = RedisServer.builder()
                .port(redisPort)
                .setting("maxmemory 128M") // 레디스는 메모리 할당에 문제가 있어서 직접 제한을 걸어달란 소리다.
                .build();
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}

