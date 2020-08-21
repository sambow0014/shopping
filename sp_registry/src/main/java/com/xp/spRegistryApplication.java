package com.xp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class spRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(spRegistryApplication.class);
    }
}
