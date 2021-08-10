package com.bjpowernode.springcloud.provider.controller;

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
        System.out.println("7002 Hystrix01 execute...");

        int i = 1/0;

        return "7002 Hystrix01 execute...";
    }

    @RequestMapping("/02")
    public String hystrix02() throws InterruptedException {
        System.out.println("7002 Hystrix02 execute...");

        Thread.sleep(5000);
        return "7002 Hystrix02 execute...";
    }


}
