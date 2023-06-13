package edu.swu.cs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
//开启远程调用openfeign
@EnableFeignClients
@SpringBootApplication
public class securityApplication {
    public static void main(String[] args){
        SpringApplication.run(securityApplication.class, args);
    }
}