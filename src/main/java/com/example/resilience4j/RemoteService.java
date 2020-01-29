package com.example.resilience4j;

public interface RemoteService {

    int circuitBreakerServiceCaller(int i);
    
    int rateLimiterServiceCaller(int i);

    int retryServiceCaller(int i);
    
}
