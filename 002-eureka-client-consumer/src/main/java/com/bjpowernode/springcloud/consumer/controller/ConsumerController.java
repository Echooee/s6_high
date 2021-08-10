package com.bjpowernode.springcloud.consumer.controller;

import com.bjpowernode.springcloud.consumer.feignservice.ProviderFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:ConsumerController
 * Package:com.bjpowernode.springcloud.consumer.controller
 * Description: 描述信息
 *
 * @date:2021/08/10 15:49
 * @author:小怪兽
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
     /*
        Feign概念介绍
            Feign：伪装者，它的作用就是将自己伪装成一个Controller(使用起来像是一个Controller)，发送http的请求
                集成了Ribbon进行负载均衡的访问
            实现步骤：
                1. 在引导类上添加新的注解
                    @EnableFeignClients
                        开启Feign的远程调用
                2. 创建一个接口，xxxService，xxxFeignService
                3. 在远程调用接口上，添加一个新的注解
                    @FeignClient("远程调用的微服务名称或集群名称")
                    可以根据微服务名称或集群名称，找到对应的ip+端口，进行远程调用或负载均衡访问
                4. 在消费者工程中，通过注入的方式，获取Feign的对象
                5. 在消费者工程中，通过控制器的方法，进行调用Feign中的方法即可实现远程调用

     */
     @Autowired
     private ProviderFeignService providerFeignService;

    @RequestMapping("/01")
    public Object consumer01(){
        Map<String, Object> resultMap = providerFeignService.provider01();

        return resultMap;
    }

    @RequestMapping("/02")
    public Object consumer02(){

        Map<String, Object> resultMap = providerFeignService.provider02("222","lisi");

        return resultMap;
    }

    @RequestMapping("/03")
    public Object consumer03(){

        Map<String,String> paramMap = new HashMap<>();

        paramMap.put("age","22");
        paramMap.put("uname","ww");

        Map<String, Object> resultMap = providerFeignService.provider03("333","wangwu",paramMap);

        return resultMap;
    }


    @RequestMapping("/04")
    public Object consumer04(){

        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("age","55");
        paramMap.put("uname","zl");

        Map<String, Object> resultMap = providerFeignService.provider04("555","赵六",paramMap);

        return resultMap;
    }


    @RequestMapping("/05")
    public Object consumer05(){

        Map<String,Object> requestBodyMap = new HashMap<>();

        requestBodyMap.put("age","66");
        requestBodyMap.put("uname","tianqi");

        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("age","55");
        paramMap.put("uname","tq");

        Map<String, Object> resultMap = providerFeignService.provider05("666","田七",requestBodyMap,paramMap);

        return resultMap;
    }

    @RequestMapping("/06")
    public Object consumer06(){

        Map<String,Object> requestBodyMap = new HashMap<>();

        requestBodyMap.put("age","77");
        requestBodyMap.put("uname","wb");

        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("age","88");
        paramMap.put("uname","wb");

        Map<String, Object> resultMap = providerFeignService.provider06("666","王八",requestBodyMap,paramMap);

        return resultMap;
    }

    @RequestMapping("/07")
    public Object consumer07(){

        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("age","88");
        paramMap.put("uname","wb");

        Map<String, Object> resultMap = providerFeignService.provider07("666","王八",paramMap);

        return resultMap;
    }


}
