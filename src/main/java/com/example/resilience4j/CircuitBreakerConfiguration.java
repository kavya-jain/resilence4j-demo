package com.example.resilience4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;

@Configuration
public class CircuitBreakerConfiguration {

    /**
     * Configuring a circuit breaker with failure rate threshold of 20%. Failure
     * rate will be calculated after every 2 calls. Count based sliding window of
     * size 2 is used which means outcome of 2 calls will be aggregated at a time
     */
    @Bean
    public CircuitBreakerConfig configure() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(20)
                .slidingWindow(2, 2, SlidingWindowType.COUNT_BASED)
                .build();
    }
    
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
