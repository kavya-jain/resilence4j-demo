package com.example.resilience4j;

import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class RemoteServiceImpl implements RemoteService {
    
    @Autowired
    private RestTemplate template;

    @Override
    public int circuitBreakerServiceCaller(int i) {
        ResponseEntity<JsonNode> response = template.getForEntity("localhost:9090/test", JsonNode.class);
        System.out.println(response.getStatusCode());
        return 0;
    }

    @Override
    public int rateLimiterServiceCaller(int i) {
        System.out.println("i : " + i);
        return 0;
    }
    
    @Override
    public int retryServiceCaller(int i) {
        if (i % 2 == 0) {
            System.out.println("called %2 at: " + ZonedDateTime.now());
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (i % 3 == 0) {
            System.out.println("called %3 at: " + ZonedDateTime.now());
            throw new ArrayIndexOutOfBoundsException(HttpStatus.BAD_REQUEST.name());
        }
        if(i % 5 == 0) {
            System.out.println("called % 5 at: " + ZonedDateTime.now());
            return i;
        }
        System.out.println("called at: " + ZonedDateTime.now());
        return 0;
    }

}
