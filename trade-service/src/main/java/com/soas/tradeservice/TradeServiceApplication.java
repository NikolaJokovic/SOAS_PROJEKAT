package com.soas.tradeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.soas.api.proxy")
public class TradeServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(TradeServiceApplication.class,args);
    }
}
