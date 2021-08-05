package com.bjpowernode.rabbitmq.direct;

import com.bjpowernode.rabbitmq.utils.ChannelUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * ClassName:GetDirectMessage
 * Package:com.bjpowernode.rabbitmq.direct
 * Description: 描述信息
 *
 * @date:2021/08/05 20:15
 * @author:小怪兽
 */
public class GetDirectMessage {
    public static void main(String[] args) throws IOException, TimeoutException {

        //获取Channel对象
        Channel channel = ChannelUtil.getChannel();

        //由于声明关系已经在发送者工程中声明
        //所以可以在此处省略声明

        //接收消息
        channel.basicConsume("directQueue",true,new DefaultConsumer(channel){
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
