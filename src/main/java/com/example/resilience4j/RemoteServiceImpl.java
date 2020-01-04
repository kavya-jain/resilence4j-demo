package com.example.resilience4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class RemoteServiceImpl implements RemoteService {
    
    @Autowired
    private RestTemplate template;

    @Override
    public int process(int i) {
        ResponseEntity<JsonNode> response = template.getForEntity("localhost:9090/test", JsonNode.class);
        System.out.println(response.getStatusCode());
        return 0;
    }

}
