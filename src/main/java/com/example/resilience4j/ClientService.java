package com.example.resilience4j;

import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;

@Service
public class ClientService {

    @Autowired
    private CircuitBreakerConfig circuitBreakerConfig;
    
    @Autowired
    private RateLimiterConfig rateLimiterConfig;

    @Autowired
    private RemoteService remoteService;
    
    @Autowired
    private RetryConfig retryConfiguration;

    public void makeRemoteServerCall() {
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        CircuitBreaker cb = registry.circuitBreaker("circuitBreaker");
        /** A circuit breaker is now associated with the method #circuitBreakerServiceCaller(int) */
        Function<Integer, Integer> decoratedFunction = CircuitBreaker.decorateFunction(cb, remoteService::circuitBreakerServiceCaller);
        for (int i = 0; i < 10; i++) {
            try {
                if(i==5)
                    Thread.sleep(3000);
                decoratedFunction.apply(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void testRateLimiter() {
        RateLimiterRegistry registry = RateLimiterRegistry.of(rateLimiterConfig);
        RateLimiter rl = registry.rateLimiter("rateLimiter");

        Function<Integer, Integer> decoratedFunction = RateLimiter.decorateFunction(rl,
                remoteService::rateLimiterServiceCaller);
        for (int i = 0; i < 10; i++) {
            try {
                decoratedFunction.apply(i);
                Thread.sleep(400);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void testRetry(int i) {
        RetryRegistry registry = RetryRegistry.of(retryConfiguration);
        Retry r = registry.retry("retry");
        Function<Integer, Integer> decoratedFunction =
                Retry.decorateFunction(r, remoteService::retryServiceCaller);
        try {
            System.out.println("i: " + i);
            decoratedFunction.apply(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
