package com.a702.finafanbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FinafanBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinafanBeApplication.class, args);
    }

}
