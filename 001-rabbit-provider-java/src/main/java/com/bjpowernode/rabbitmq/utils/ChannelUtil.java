package com.bjpowernode.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * ClassName:ChannelUtil
 * Package:com.bjpowernode.rabbitmq.utils
 * Description: 描述信息
 *
 * @date:2021/08/03 20:50
 * @author:小怪兽
 */
public class ChannelUtil {

    public static Connection connection;

    public static Channel channel;

    public static Channel getChannel() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("192.168.119.129");
        factory.setVirtualHost("/");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("root");

        connection = factory.newConnection();

        channel = connection.createChannel();

        return channel;
    }

    public static void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}
