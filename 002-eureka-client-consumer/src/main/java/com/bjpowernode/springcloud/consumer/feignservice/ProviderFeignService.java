package com.bjpowernode.springcloud.consumer.feignservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ClassName:ProviderFeignService
 * Package:com.bjpowernode.springcloud.consumer.feignservice
 * Description: 描述信息
 *
 * @date:2021/08/10 15:51
 * @author:小怪兽
 */
@FeignClient(name = "EUREKA-CLIENT-PROVIDER")
public interface ProviderFeignService {
     /*
        通过微服务的名称和请求的接口地址，就能够找到对应的控制器
            @FeignClient(name = "EUREKA-CLIENT-PROVIDER") 相当于将当前接口通过动态代理的方式创建代理对象，交给spring容器进行管理
            +
            @RequestMapping("/provider/01")
            =
            http://localhost:7001/provider/01
            http://localhost:7002/provider/01

        创建的Feign的远程调用方法时
            请求地址、请求参数、返回值 必须 和 远程调用的微服务 的 方法一致
            方法名称可以不一致
     */

    @RequestMapping("/provider/01")
    public Map<String,Object> provider01();

    @RequestMapping("/provider/02/{id}/{username}")
    public Map<String,Object> provider02(@PathVariable("id") String id , @PathVariable String username);

    //远程调用时，传递的集合，是被封装到请求体中
    //必须使用@RequestBody进行获取
    @RequestMapping("/provider/03/{id}/{username}")
    public Map<String,Object> provider03(@PathVariable("id") String id ,
                                         @PathVariable String username ,
//                                       当前的Map集合是被封装到了请求体中
                                         @RequestParam Map<String,String> paramMap);


    @RequestMapping("/provider/04/{id}/{username}")
    public Map<String,Object> provider04(@PathVariable("id") String id ,
                                         @PathVariable String username,
                                         @RequestBody Map<String,Object> requestBodyMap);


    @RequestMapping("/provider/05/{id}/{username}")
    public Map<String,Object> provider05(@PathVariable("id") String id ,
                                         @PathVariable String username,
                                         @RequestBody Map<String,Object> requestBodyMap,
                                         //如果想要通过请求参数进行传递
                                         //在Feign中必须定义@RequestParam注解
                                         //代表通过键值对参数的方式进行传递
                                         @RequestParam Map<String,Object> paramMap);


    //    @RequestMapping("/provider/06/{id}/{name}")
    @PutMapping("/provider/06/{id}/{name}")
    public Map<String,Object> provider06(@PathVariable("id") String id ,
                                         @PathVariable String name,
                                         @RequestBody Map<String,Object> requestBodyMap,
                                         @RequestParam Map<String,Object> paramMap);


    @DeleteMapping("/provider/07/{id}/{name}")
    public Map<String,Object> provider07(@PathVariable("id") String id ,
                                         @PathVariable String name,
                                         @RequestParam Map<String,Object> paramMap);


}
