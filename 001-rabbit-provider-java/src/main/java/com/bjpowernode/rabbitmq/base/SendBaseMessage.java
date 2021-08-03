package com.bjpowernode.rabbitmq.base;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * ClassName:SendBaseMessage
 * Package:com.bjpowernode.rabbitmq.base
 * Description: 描述信息
 *
 * @date:2021/08/02 21:16
 * @author:小怪兽
 */
public class SendBaseMessage {

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建RabbitMQ的连接工厂对象
        ConnectionFactory factory = new ConnectionFactory();
        //设置连接参数：host、port、username、password、vHost
        factory.setHost("192.168.119.129");
        //端口号：15672是管控台的端口，客户端端口5672
        factory.setPort(5672);
        //用户名
        factory.setUsername("root");
        //密码
        factory.setPassword("root");
        //设置操作的vHost，文件夹，默认是/
        factory.setVirtualHost("/");

        //获取连接对象
        Connection connection = factory.newConnection();

        //根据连接对象创建管道对象
        Channel channel = connection.createChannel();

        //声明消息队列，在发送消息之前，必须要先声明消息队列，否则报错
        /*
            Queue.DeclareOk queueDeclare(
                                    String queue,           消息队列名称
                                    boolean durable,        是否持久化
                                    boolean exclusive,      是否排外
                                    boolean autoDelete,     是否自动删除，true，没有消费者消费时，该消息队列会被自动删除
                                    Map<String, Object> arguments   消息队列的属性，通常null
            ) throws IOException;
         */
        channel.queueDeclare("basicQueue",true,false,false,null);

        /*
            channel对象：
                创建交换机
                创建消息队列
                创建绑定关系
                发送消息
                监听/接收消息
                ...
         */

        //发送消息
        String message = "basicMessage";

        //发送消息
        //void basicPublish(
        //      String exchange,        交互机名称，如果没有交换机则设置为空字符串即可
        //      String routingKey,      路由键，如果发送到指定的消息队列中，则指定消息队列名称
        //      BasicProperties props,  消息属性，通常设置为null
        //      byte[] body)            消息实体的byte数组
        // throws IOException;
        channel.basicPublish("","basicQueue",null,message.getBytes());

        //关闭资源
        channel.close();
        connection.close();
    }

}
