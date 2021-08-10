package com.bjpowernode.springcloud.provider.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:ProviderController
 * Package:com.bjpowernode.springcloud.provider.controller
 * Description: 描述信息
 *
 * @date:2021/08/07 20:13
 * @author:小怪兽
 */

@RestController
@RequestMapping("/provider")
public class ProviderController {

    @RequestMapping("/01")
    public Map<String,Object> provider01(){

        System.out.println("提供者工程 provider01 被调用了...");

        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("code",10000);
        resultMap.put("msg","001-eureka-client-provider 7001 provider01 请求成功...");
        resultMap.put("data",null);

        return resultMap;
    }

    @RequestMapping("/02/{id}/{username}")
    public Map<String,Object> provider02(@PathVariable("id") String id , @PathVariable String username){
        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("code",10000);
        resultMap.put("msg","001-eureka-client-provider 7001 provider02 请求成功...");
        resultMap.put("data",id+" "+username);

        return resultMap;
    }

    @RequestMapping("/03/{id}/{username}")
    public Map<String,Object> provider03(@PathVariable("id") String id ,
                                         @PathVariable String username ,
//                                         String age,
//                                         String uname,
                                         //如果接收的参数为Map集合，需要从请求的参数中进行获取
                                         //必须添加@RequestParam注解
                                         @RequestParam Map<String,String> paramMap){
        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("code",10000);
        resultMap.put("msg","001-eureka-client-provider 7001 provider03 请求成功...");
        resultMap.put("data",id+" "+username);
        resultMap.put("dataRequestBody",paramMap);

        return resultMap;
    }

    @RequestMapping("/04/{id}/{username}")
    public Map<String,Object> provider04(@PathVariable("id") String id ,
                                         @PathVariable String username,
                                         //如果发送请求的数据，封装到请求体中，
                                         //必须使用注解@RequestBody进行获取参数并映射
                                         @RequestBody Map<String,Object> requestBodyMap){
        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("code",10000);
        resultMap.put("msg","001-eureka-client-provider 7001 provider04 请求成功...");
        resultMap.put("data",id+" "+username);
        resultMap.put("dataMap",requestBodyMap);

        return resultMap;
    }

    @RequestMapping("/05/{id}/{username}")
//    @PostMapping("/05/{id}/{username}")
    public Map<String,Object> provider05(@PathVariable("id") String id ,
                                         @PathVariable String username,
                                         //如果发送请求的数据，封装到请求体中，
                                         //必须使用注解@RequestBody进行获取参数并映射
                                         @RequestBody Map<String,Object> requestBodyMap,
                                         @RequestParam Map<String,Object> paramMap){
        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("code",10000);
        resultMap.put("msg","001-eureka-client-provider 7001 provider05 请求成功...");
        resultMap.put("data",id+" "+username);
        resultMap.put("dataRequestBodyMap",requestBodyMap);
        resultMap.put("paramMap",paramMap);

        return resultMap;
        }

    //    @PutMapping("/06/{id}/{name}")
    @PutMapping("/06/{id}/{name}")
    public Map<String,Object> provider06(@PathVariable("id") String id ,
                                         @PathVariable String name,
                                         //如果发送请求的数据，封装到请求体中，
                                         //必须使用注解@RequestBody进行获取参数并映射
                                         @RequestBody Map<String,Object> requestBodyMap,
                                         @RequestParam Map<String,Object> paramMap){
        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("code",10000);
        resultMap.put("msg","001-eureka-client-provider 7001 provider06 请求成功...");
        resultMap.put("data",id+" "+name);
        resultMap.put("dataRequestBodyMap",requestBodyMap);
        resultMap.put("paramMap",paramMap);

        System.out.println(resultMap);

        return resultMap;
    }

    @DeleteMapping("/07/{id}/{name}")
    public Map<String,Object> provider07(@PathVariable("id") String id ,
                                         @PathVariable String name,
                                         @RequestParam Map<String,Object> paramMap){
        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("code",10000);
        resultMap.put("msg","001-eureka-client-provider 7001 provider07 请求成功...");
        resultMap.put("data",id+" "+name);
        resultMap.put("paramMap",paramMap);

        System.out.println(resultMap);

        return resultMap;
    }







}
