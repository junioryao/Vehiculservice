package com.example.vehiclesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableAsync
public class VehiclesApiApplication {

    @Bean
    public ExecutorService ThreadPool(){
        return Executors.newFixedThreadPool(10) ;
    }


    @Bean
    @LoadBalanced
    public WebClient.Builder getWebClient(){
        return WebClient.builder();
    }

    public static void main(String[] args) {
        SpringApplication.run(VehiclesApiApplication.class, args);
    }

}
