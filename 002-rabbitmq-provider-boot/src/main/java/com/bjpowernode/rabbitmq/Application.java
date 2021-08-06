package com.bjpowernode.rabbitmq;

import com.bjpowernode.rabbitmq.provider.SendMessageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);

        SendMessageService service = app.getBean(SendMessageService.class);

//        service.sendBaseMessage();
//        service.sendDirectMessage();
//        service.sendFanoutMessage();
        service.sendTopicMessage();
    }


}
