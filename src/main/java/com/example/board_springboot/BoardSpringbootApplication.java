package com.example.board_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BoardSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardSpringbootApplication.class, args);
    }

}
