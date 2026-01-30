package com.geprice.controller;

import com.geprice.pojo.Health;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.Instant;

@RestController
@RequestMapping("/api/health")
public class SystemController {

    @GetMapping
    public Health health() {
        return Health.builder()
                .status("ok")
                .timestamp(Instant.now(Clock.systemUTC()))
                .build();
    }
}
