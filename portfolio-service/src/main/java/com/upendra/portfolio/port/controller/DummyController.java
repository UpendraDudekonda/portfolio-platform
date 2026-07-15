package com.upendra.portfolio.port.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/portfolio")
public class DummyController {
	
	@GetMapping("/greet")
    public Map<String, String> greet(
            @RequestHeader("X-User-UUID") String uuid,
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Role") String role) {

        return Map.of(
                "message", "Reached Portfolio Service",
                "uuid", uuid,
                "email", email,
                "role", role
        );
    }

}
