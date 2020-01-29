package com.example.resilience4j.configuration;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpServerErrorException;
import io.github.resilience4j.retry.RetryConfig;

@Configuration
public class RetryConfiguration {
    
    @Bean
    public RetryConfig retryConfig() {
        return
                RetryConfig.custom()
                .maxAttempts(15)
                .retryExceptions(HttpServerErrorException.class)
                .ignoreExceptions(ArrayIndexOutOfBoundsException.class)
                .waitDuration(Duration.ofSeconds(2))
                .retryOnResult(result -> result.equals(0))
                .build();
    }

}
