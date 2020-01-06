package com.example.resilience4j;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Service
public class ClientService {

    @Autowired
    private CircuitBreakerConfig config;

    @Autowired
    private RemoteService remoteService;

    public void makeRemoteServerCall() {
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        CircuitBreaker cb = registry.circuitBreaker("circuitBreaker");
        /** A circuit breaker is now associated with the method #process(int) */
        Function<Integer, Integer> decoratedFunction = CircuitBreaker.decorateFunction(cb, remoteService::process);
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

}
