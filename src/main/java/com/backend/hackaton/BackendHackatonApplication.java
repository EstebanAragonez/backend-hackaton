package com.backend.hackaton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendHackatonApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendHackatonApplication.class, args);
    }
}

