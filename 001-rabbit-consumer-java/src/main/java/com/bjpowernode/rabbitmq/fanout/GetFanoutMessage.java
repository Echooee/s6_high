package com.bjpowernode.rabbitmq.fanout;

import com.bjpowernode.rabbitmq.utils.ChannelUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * ClassName:GetFanoutMessage
 * Package:com.bjpowernode.rabbitmq.fanout
 * Description: 描述信息
 *
 * @date:2021/08/05 20:59
 * @author:小怪兽
 */
public class GetFanoutMessage {
    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = ChannelUtil.getChannel();

        //定义随机名称的消息队列
        channel.exchangeDeclare("fanoutExchange","fanout");

        //我们使用随机队列名称，进行发送fanout类型的消息
        /*
        queueDeclare("", false, true, true, null);
         */
//        String queueName = channel.queueDeclare().getQueue();
//        System.out.println("randomQueueName :::>>> "+queueName);
//        channel.queueBind(queueName,"fanoutExchange","");
//        channel.basicConsume(queueName,true,new DefaultConsumer(channel){

        channel.queueDeclare("fanoutQueue2",false,true,true,null);

        channel.queueBind("fanoutQueue2","fanoutExchange","");

        channel.basicConsume("fanoutQueue2",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                long deliveryTag = envelope.getDeliveryTag();
                String routingKey = envelope.getRoutingKey();
                String exchange = envelope.getExchange();

                System.out.println("deliveryTag :::>>> "+deliveryTag);
                System.out.println("routingKey :::>>> "+routingKey);
                System.out.println("exchange :::>>> "+exchange);
                System.out.println("body :::>>> "+new String(body));
                System.out.println("consumerTag :::>>> "+consumerTag);
                System.out.println("-------华丽的分割线-------");
            }
        });

    }
}
