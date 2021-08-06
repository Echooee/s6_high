package com.bjpowernode.rabbitmq.topic;

import com.bjpowernode.rabbitmq.utils.ChannelUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * ClassName:SendTopicMessage
 * Package:com.bjpowernode.rabbitmq.topic
 * Description: 描述信息
 *
 * @date:2021/08/06 15:05
 * @author:小怪兽
 */
public class SendTopicMessage {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ChannelUtil.getChannel();

        //创建消息
        String message ="topicMessage routingKey aa";
//        String message ="topicMessage routingKey aa.bb";
//        String message ="topicMessage routingKey aa.bb.cc";

        channel.exchangeDeclare("topicExchange","topic");

        channel.queueDeclare("topicQueue1",true,false,false,null);
        channel.queueDeclare("topicQueue2",true,false,false,null);
        channel.queueDeclare("topicQueue3",true,false,false,null);

        channel.queueBind("topicQueue1","topicExchange","aa");
        channel.queueBind("topicQueue2","topicExchange","aa.*");
        channel.queueBind("topicQueue3","topicExchange","aa.#");

        //发送消息
//        channel.basicPublish("topicExchange","aa",null,message.getBytes());
//        channel.basicPublish("topicExchange","aa.bb",null,message.getBytes());
//        channel.basicPublish("topicExchange","aa.bb.cc",null,message.getBytes());

        ChannelUtil.close();
    }

}
