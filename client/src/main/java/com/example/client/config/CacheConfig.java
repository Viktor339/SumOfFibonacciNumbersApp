package com.example.client.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class CacheConfig {

    private final CacheProperties cacheProperties;

    @Bean
    public Cache<Integer, Mono<Long>> cache() {

        return Caffeine.newBuilder()
                .maximumSize(cacheProperties.getMaxSize())
                .expireAfterWrite(cacheProperties.getTimeToLive())
                .build();
    }

}
