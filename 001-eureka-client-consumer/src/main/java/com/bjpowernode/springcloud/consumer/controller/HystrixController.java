package com.bjpowernode.springcloud.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:HystrixController
 * Package:com.bjpowernode.springcloud.consumer.controller
 * Description: 描述信息
 *
 * @date:2021/08/10 19:55
 * @author:小怪兽
 */
@RestController
@RequestMapping("/consumer/hystrix")
public class HystrixController {

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/01")
    @HystrixCommand(fallbackMethod = "errorMethod")
    public String hystrix01() {
        String url = "http://eureka-client-provider/hystrix/01";
        String body = restTemplate.getForObject(url, String.class);
        return body;

    }

    public String errorMethod() {
        return "网络繁忙，请稍后再试...";
    }

    @RequestMapping("/02")
    @HystrixCommand(
            fallbackMethod = "errorTimeOutMethod",
            commandProperties = {
                    @HystrixProperty(
                            name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "2000"
                    )
            }
    )
    public String hystrix02() {
        String url = "http://eureka-client-provider/hystrix/02";
        String body = restTemplate.getForObject(url, String.class);
        return body;

    }
    public String errorTimeOutMethod() {
        return "网络延迟，请稍后再试...";
    }

    /**
     * 服务的熔断限流操作
     *
     * @return
     */
    @RequestMapping("/03/{code}")
    @HystrixCommand(
            fallbackMethod = "error",
            commandProperties = {
                    //开启熔断器
                    @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
                    //断路器熔断的最小请求数
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
                    //熔断异常的百分比
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50"),
                    //时间窗口期
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000")
            }
    )
    public String hystrix03(@PathVariable Integer code){
        String url = "http://eureka-client-provider/hystrix/03/{code}";


        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("code",code);

        String body = restTemplate.getForObject(url, String.class,paramMap);
        return body;
    }

    public String error(Integer code){
        return "当前服务被熔断，请稍后再试..."+code;
    }

}
