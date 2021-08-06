package com.bjpowernode.rabbitmq.topic;

import com.bjpowernode.rabbitmq.utils.ChannelUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * ClassName:GetTopicMessage
 * Package:com.bjpowernode.rabbitmq.topic
 * Description: 描述信息
 *
 * @date:2021/08/06 15:12
 * @author:小怪兽
 */
public class GetTopicMessage {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ChannelUtil.getChannel();

//        channel.basicConsume("topicQueue1",true,new DefaultConsumer(channel){
//        channel.basicConsume("topicQueue2",true,new DefaultConsumer(channel){
        channel.basicConsume("topicQueue3",true,new DefaultConsumer(channel){
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
