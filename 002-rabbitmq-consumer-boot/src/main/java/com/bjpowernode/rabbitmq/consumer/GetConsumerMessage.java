package com.bjpowernode.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ClassName:GetConsumerMessage
 * Package:com.bjpowernode.rabbitmq.consumer
 * Description: 描述信息
 *
 * @date:2021/08/06 15:51
 * @author:小怪兽
 */
@Component
public class GetConsumerMessage {

    //普通方式
    @RabbitListener(queues = {"baseQueue"})
    public void getBaseMessage(String message) {
        System.out.println(message);
    }

    //direct方式
    @RabbitListener(queues = "bootDirectQueue")
    public void getDirectMessage(String message) {
        System.out.println(message);
    }

    @RabbitListener(
            bindings = {
//                    绑定随机名称的消息队列和交换机
                    @QueueBinding(
                            value = @Queue(durable = "false",exclusive = "true",autoDelete = "true"),
                            exchange = @Exchange(name = "bootFanoutExchange",type = "fanout")
                    )
//                    绑定固定名称的消息队列和交换机
//                    @QueueBinding(
//                            value = @Queue(name = "bootFanoutQueue1",durable = "false", exclusive = "true", autoDelete = "true"),
//                            exchange = @Exchange(name = "bootFanoutExchange", type = "fanout")
//                    )
            }
    )
    public void getFanoutMessage(String message) {
        System.out.println(message);
    }


//    @RabbitListener(queues = "topicQueue1")
    @RabbitListener(queues = "topicQueue2")
//    @RabbitListener(queues = "topicQueue3")
    public void getTopicMessage(String message) {
        System.out.println(message);
    }
}
