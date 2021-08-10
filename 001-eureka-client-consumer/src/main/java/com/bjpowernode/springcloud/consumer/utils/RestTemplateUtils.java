package com.bjpowernode.springcloud.consumer.utils;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * ClassName:RestTemplateUtils
 * Package:com.bjpowernode.springcloud.consumer.utils
 * Description: 描述信息
 *
 * @date:2021/8/6 9:22
 * @author:动力节点
 */
@Configuration
public class RestTemplateUtils {

 /*
        开启Ribbon负载均衡访问
        在使用Ribbon的负载均衡访问时，无法通过localhost:xxx/xx进行访问了
            我们在配置微服务的集群时，微服务的名称必须保持一致
            我们就可以使用微服务名称进行远程访问
            将
                http://localhost:7001/xxx/xxx
            改为
                http://EUREKA-CLIENT-PROVIDER/xxx/xxx
     */

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }



}
