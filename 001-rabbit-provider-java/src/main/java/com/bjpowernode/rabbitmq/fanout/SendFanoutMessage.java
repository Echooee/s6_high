package com.bjpowernode.rabbitmq.fanout;

import com.bjpowernode.rabbitmq.utils.ChannelUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * ClassName:SendFanoutMessage
 * Package:com.bjpowernode.rabbitmq.fanout
 * Description: 描述信息
 *
 * @date:2021/08/05 20:58
 * @author:小怪兽
 */
public class SendFanoutMessage {
    public static void main(String[] args) throws  IOException, TimeoutException {

        //获取Channel对象
        Channel channel = ChannelUtil.getChannel();

        //准备消息
        String message = "fanoutMessage";

        //声明关系

        //先这样绑定，看看有没有什么问题
        //发送的消息确实被发送到了fanout交换机当中
        //也通过交换机将消息路由到了消息队列中
        //但是我们获取出来的是单个监听器获取到
        channel.exchangeDeclare("fanoutExchange","fanout");

        /*
        当前的消息队列是随机队列名称，但是我们设置的队列属性是非持久化、排外、自动删除的特定
            来实现fanout的消息队列方式
        queueDeclare("", false, true, true, null);
         */
//        channel.queueDeclare("fanoutQueue",false,true,true,null);
//        channel.queueBind("fanoutQueue","fanoutExchange","");

        //发送消息
        channel.basicPublish("fanoutExchange","",null,message.getBytes());

        ChannelUtil.close();
    }
}
