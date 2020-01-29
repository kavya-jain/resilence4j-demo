package com.example.resilience4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    private ClientService client;

    @GetMapping("/test-circuit-breaker")
    public ResponseEntity<String> testCircuitBreaker() {
        client.makeRemoteServerCall();
        return ResponseEntity.ok().body("Testing completed");
    }
    
    @GetMapping("/test-rate-limiter")
    public ResponseEntity<String> testRateLimiter() {
        client.testRateLimiter();
        return ResponseEntity.ok().body("Testing completed");
    }
    
    @GetMapping("/test-retry/{i}")
    public ResponseEntity<String> testRetry(@PathVariable int i) {
        client.testRetry(i);
        return ResponseEntity.ok().body("Testing completed");
    }

}
