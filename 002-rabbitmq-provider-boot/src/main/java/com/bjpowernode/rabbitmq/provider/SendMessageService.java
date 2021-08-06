package com.bjpowernode.rabbitmq.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName:SendMessageService
 * Package:com.bjpowernode.rabbitmq.provider
 * Description: 描述信息
 *
 * @date:2021/08/06 15:45
 * @author:小怪兽
 */
@Component
public class SendMessageService {

    @Autowired
    private AmqpTemplate template;

    public void sendBaseMessage(){
        template.convertAndSend("baseQueue","providerbaseMessage");
    }

    public void sendDirectMessage() {
        template.convertAndSend("bootDirectExchange","bootDirectRoutingKey","providerbootDirectMessage");
    }

    public void sendFanoutMessage(){
        template.convertAndSend("bootFanoutExchange","","bootDirectMessage");
    }

    public void sendTopicMessage(){
        template.convertAndSend("bootTopicExchange","aa","bootFanoutMessage  aa");
        template.convertAndSend("bootTopicExchange","aa.bb","bootFanoutMessage  aa.bb");
        template.convertAndSend("bootTopicExchange","aa.bb.cc","bootFanoutMessage  aa.bb.cc");
    }
}
