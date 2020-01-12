package com.example.resilience4j.configuration;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;

@Configuration
public class RateLimiterConfiguration {

    /**
     * RateLimiter configuration: In every 1 second, 2 calls are permitted and
     * after that every call will wait for 100 miliseconds before get timed out
     */
    @Bean
    public RateLimiterConfig rateLimiterConfig() {
        return RateLimiterConfig.custom()
                .limitForPeriod(2)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ofMillis(100))
                .build();
    }
    
}
