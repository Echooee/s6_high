package com.bjpowernode.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Eureka_6001_Application {

    public static void main(String[] args) {
        SpringApplication.run(Eureka_6001_Application.class, args);
    }

}
