package com.bjpowernode.springcloud.provider.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:HystrixController
 * Package:com.bjpowernode.springcloud.provider.controller
 * Description: 描述信息
 *
 * @date:2021/08/10 19:50
 * @author:小怪兽
 */
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @RequestMapping("/01")
    public String hystrix01() {
        System.out.println("7001 Hystrix01 execute...");

        int i = 1/0;

        return "7001 Hystrix01 execute...";
    }

    @RequestMapping("/02")
    public String hystrix02() throws InterruptedException {
        System.out.println("7001 Hystrix02 execute...");

        Thread.sleep(5000);
        return "7001 Hystrix02 execute...";
    }

    @RequestMapping("/03/{code}")
    public String hystrix03(@PathVariable Integer code){

        System.out.println("7001 Hystrix03 execute...");

        if(code <= 0){
            int i = 1/0;
        }

        return "7001 Hystrix03 execute...";

    }


}
