package com.bjpowernode.rabbitmq.direct;

import com.bjpowernode.rabbitmq.utils.ChannelUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * ClassName:SendDirectMessage
 * Package:com.bjpowernode.rabbitmq.direct
 * Description: 描述信息
 *
 * @date:2021/08/03 20:49
 * @author:小怪兽
 */
public class SendDirectMessage {
    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = ChannelUtil.getChannel();

        //准备消息内容
        String message = "directMessage";

        //声明交换机、消息队列、绑定关系
        channel.exchangeDeclare("directExchange","direct");

         /*
        Queue.DeclareOk queueDeclare(
                String queue,
                boolean durable,
                exclusive,
                boolean autoDelete,
                Map<String, Object> arguments) throws IOException;
         */
        channel.queueDeclare("directQueue",true,false,false,null);

        /*
        Queue.BindOk queueBind(String queue, String exchange, String routingKey) throws IOException;
        */
        channel.queueBind("directQueue","directExchange","directRoutingKey");

        //发送消息
        /*
        void basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body) throws IOException;
        */
        channel.basicPublish("directExchange","directRoutingKey",null,message.getBytes());

        //关闭资源
        ChannelUtil.close();





    }
}
