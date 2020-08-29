package com.xp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpItemServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpItemServiceApplication.class);
    }
}
