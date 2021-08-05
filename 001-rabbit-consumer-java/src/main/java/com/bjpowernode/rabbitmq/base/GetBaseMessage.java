package com.bjpowernode.rabbitmq.base;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * ClassName:GetBaseMessage
 * Package:com.bjpowernode.rabbitmq.base
 * Description: 描述信息
 *
 * @date:2021/08/02 21:17
 * @author:小怪兽
 */
public class GetBaseMessage {

    public static void main(String[] args) throws IOException, TimeoutException {

        //获取工场对象
        ConnectionFactory factory = new ConnectionFactory();
        //设置属性
        factory.setHost("192.168.119.129");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("root");
        factory.setPassword("root");
        //创建连接对象
        Connection connection = factory.newConnection();
        //创建管道对象
        Channel channel = connection.createChannel();

        channel.basicConsume("basicQueue",true,new DefaultConsumer(channel){

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
        //消息的消费者，不要关闭资源
        //如果关闭资源，会无法持续性的接收到消息队列中的消息

    }
}
