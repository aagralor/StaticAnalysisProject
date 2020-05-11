package com.example.app.ws;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingResource {

    @GetMapping(path = "/ping")
    public ResponseEntity<Map<String, String>> ping() {
        Map<String, String> map = new HashMap<>();
        map.put("ping", "pong");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(path = "/webhook")
    public ResponseEntity<Object> webhook(@RequestBody Object ob,
    		@RequestHeader("User-Agent") String userAgent,
    		@RequestHeader("X-GitHub-Delivery") String delivery,
    		@RequestHeader("X-GitHub-Event") String event) {

    	System.out.println(ob);
    	System.out.println(userAgent);
    	System.out.println(delivery);
    	System.out.println(event);

        return new ResponseEntity<>(ob, HttpStatus.OK);
    }
}
