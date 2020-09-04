package com.xp;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.xp.item.mapper")
public class SpItemServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpItemServiceApplication.class);
    }
}
