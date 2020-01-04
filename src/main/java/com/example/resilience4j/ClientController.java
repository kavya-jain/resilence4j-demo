package com.example.resilience4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    private ClientService client;

    @GetMapping("/test-client")
    public ResponseEntity<String> testClient() {
        client.makeRemoteServerCall();
        return ResponseEntity.ok().body("Testing completed");
    }

}
