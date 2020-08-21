package com.xp;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringCloudApplication
public class spGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(spGatewayApplication.class);
    }
}
