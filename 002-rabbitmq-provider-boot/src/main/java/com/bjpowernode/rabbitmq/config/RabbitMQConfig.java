package com.bjpowernode.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * ClassName:RabbitMQConfig
 * Package:com.bjpowernode.rabbitmq.config
 * Description: 描述信息
 *
 * @date:2021/08/06 15:47
 * @author:小怪兽
 */
@Configuration
public class RabbitMQConfig {

    //声明普通的消息队列
    @Bean
    public Queue baseQueue() {
        return new Queue("baseQueue");
    }
    //-----------------------------------------------------------------------

    //direct类型
    //声明交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("bootDirectExchange");
    }

    @Bean
    public Queue bootDirectQueue() {
        return new Queue("bootDirectQueue");
    }



    @Bean
    public Binding directBiding(Queue bootDirectQueue,DirectExchange directExchange) {
        return BindingBuilder.bind(bootDirectQueue).to(directExchange).with("bootDirectRoutingKey");
    }

    //--------------------------------------------------------------------------------------
    //fanout类型
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("bootFanoutExchange");
    }


    //----------------------------------------------------------------------------
    //topic类型

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("bootTopicExchange");
    }

    @Bean
    public Queue topicQueue1(){
        return new Queue("topicQueue1");
    }

    @Bean
    public Queue topicQueue2(){
        return new Queue("topicQueue2");
    }

    @Bean
    public Queue topicQueue3(){
        return new Queue("topicQueue3");
    }

    @Bean
    public Binding topicBinding1(Queue topicQueue1,TopicExchange topicExchange){
        return BindingBuilder.bind(topicQueue1).to(topicExchange).with("aa");
    }

    @Bean
    public Binding topicBinding2(Queue topicQueue2,TopicExchange topicExchange){
        return BindingBuilder.bind(topicQueue2).to(topicExchange).with("aa.*");
    }

    @Bean
    public Binding topicBinding3(Queue topicQueue3,TopicExchange topicExchange){
        return BindingBuilder.bind(topicQueue3).to(topicExchange).with("aa.#");
    }

}
