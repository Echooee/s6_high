package com.bjpowernode.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class Provider_7001_Application {

    public static void main(String[] args) {
        SpringApplication.run(Provider_7001_Application.class, args);
    }

}
