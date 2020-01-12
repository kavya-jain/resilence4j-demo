package com.example.resilience4j.configuration;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;

@Configuration
public class CircuitBreakerConfiguration {

    /**
     * Configuring a circuit breaker. Failure rate will be calculated after every 2
     * calls. Count based sliding window of size 2 is used which means outcome of 20
     * calls will be aggregated at a time. It waits for 3 seconds in Open state
     * before automatically transitioning to Half-Open state
     * 
     * @throws CallNotPermittedException when the CircuitBreaker is in open state
     *                                   and does not permit further calls
     */
    @Bean
    public CircuitBreakerConfig configure() {
        return CircuitBreakerConfig.custom()
                .waitDurationInOpenState(Duration.ofSeconds(3))
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .permittedNumberOfCallsInHalfOpenState(1)
                .slidingWindow(20, 2, SlidingWindowType.COUNT_BASED)
                .build();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
