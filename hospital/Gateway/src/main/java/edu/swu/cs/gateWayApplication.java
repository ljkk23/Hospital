package edu.swu.cs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class gateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(gateWayApplication.class, args);
    }
}
