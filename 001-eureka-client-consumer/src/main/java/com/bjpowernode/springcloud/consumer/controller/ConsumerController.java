package com.bjpowernode.springcloud.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:ConsumerController
 * Package:com.bjpowernode.springcloud.consumer.controller
 * Description: 描述信息
 *
 * @date:2021/08/07 20:14
 * @author:小怪兽
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/01")
    public Object consumer01(){
        //调用无参的get方式的请求传递

        //http://localhost:7001/provider/01
        /*
        参数1：url地址
        参数2：响应字节码类型，返回值类型
        参数3：封装的参数
        <T> ResponseEntity<T> getForEntity(
                String url,
                Class<T> responseType,
                Map<String, ?> uriVariables)
         */

//        String url = "http://localhost:7001/provider/01";
        String url = "http://EUREKA-CLIENT-PROVIDER/provider/01";

        /*
        RestTemplate：
            getForEntity方法：
                返回值为ResponseEntity，响应实体对象
                可以从中获取到响应头、响应体数据、响应状态码信息等参数
         */
        ResponseEntity<Map> entity = restTemplate.getForEntity(url, Map.class);

        System.out.println("StatusCode : "+entity.getStatusCode());
        System.out.println("StatusCodeValue : "+entity.getStatusCodeValue());
        //提供者返回的数据
        System.out.println("Body :1 "+entity.getBody());
        System.out.println("Headers : "+entity.getHeaders());

        /*
        RestTemplate：
            getForObject方法：
            <T> <T> getForObject(
                String url,
                Class<T> responseType,
                Map<String, ?> uriVariables)
                返回值就是调用的响应数据，就是我们entity中的body数据
        */
//        Map resultMap = restTemplate.getForObject(url, Map.class);

//        System.out.println("resultMap : "+resultMap);

        //状态码的标记，公司内部定义
        //code=10000代表请求成功
        //code=10001代表请求失败
        //msg=xxx
        //data=x/{}/[]
        return entity.getBody();
    }

    @RequestMapping("/02")
    public Object consumer02(){
        //Get方式的rest ful参数传递
        //http://xxx:80xx/provider/02/?/?
        //在参数中，进行{变量名称}的方式进行传递参数
//        String url = "http://localhost:7001/provider/02/{id}/{username}";
        String url = "http://EUREKA-CLIENT-PROVIDER/provider/02/{id}/{username}";


        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("id","111");
        paramMap.put("username","zhangsan");

        Map resultMap = restTemplate.getForObject(url, Map.class, paramMap);

        return resultMap;
    }

    @RequestMapping("/03")
    public Object consumer03(){

//        http://xxx:80xx/provider/03?key=value
//        String url = "http://localhost:7001/provider/03/{id}/{username}?age=18&uname=zs";
          String url = "http://EUREKA-CLIENT-PROVIDER/provider/03/{id}/{username}?age=18&uname=zs";

        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("id","222");
        paramMap.put("username","lisi");

        Map resultMap = restTemplate.getForObject(url, Map.class, paramMap);

        return resultMap;

    }

    /**
     * Post方式的请求调用
     *  http://xxx:80xx/provider/02/?/?
     *  http://xxx:80xx/provider/02?key=value
     *  http://xxx:80xx/provider/02
     * @return
     */
    @RequestMapping("/04")
    public Object consumer04(){

        String url = "http://EUREKA-CLIENT-PROVIDER/provider/04/{id}/{username}";
//        String url = "http://localhost:7001/provider/04/{id}/{username}";

        //封装的请求地址栏中的参数信息
        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("id",111);
        paramMap.put("username","wangwu");

        //封装请求体中的参数信息
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("age",22);
        requestMap.put("data","深圳市宝安区");


        /*
        参数1：请求地址的url
        参数2：请求体中的数据，通常封装键值对，Map或实体类
        参数3：返回值字节码类型
        参数4：请求参数，地址栏中的请求参数
        <T> ResponseEntity<T> postForEntity(
                                            String url,
                                            @Nullable Object request,
                                            Class<T> responseType,
			                                Map<String, ?> uriVariables)
         */
        ResponseEntity<Map> entity = restTemplate.postForEntity(url, requestMap, Map.class, paramMap);


        System.out.println("StatusCode : "+entity.getStatusCode());
        System.out.println("StatusCodeValue : "+entity.getStatusCodeValue());
        System.out.println("Headers : "+entity.getHeaders());
        System.out.println("Body : "+entity.getBody());

        /*
        <T> T postForObject(
                            String url,
                            @Nullable Object request,
                            Class<T> responseType,
			                Map<String, ?> uriVariables)
         */
//        restTemplate.postForObject()

        return entity.getBody();
    }

    /**
     * 接收参数的方式：
     *      1.地址栏中的rest ful风格的请求参数传递
     *              使用@PathVariable注解进行接收
     *      2.地址栏中通过get方式的参数拼接传递，xxx?k=v&k1=v1...
     *              使用@RequestParam(...)进行参数接收
     *      3.通过请求体中进行参数传递
     *              使用@RequestBody进行参数的接收
     * @return
     */
    @RequestMapping("/05")
    public Object consumer05(){

        String url = "http://EUREKA-CLIENT-PROVIDER/provider/05/{id}/{username}?desc=asdf";
//        String url = "http://localhost:7001/provider/05/{id}/{username}?desc=asdf";

        //封装的请求地址栏中的参数信息
        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("id",222);
        paramMap.put("username","zhaoliu");

        //封装请求体中的参数信息
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("age",33);
        requestMap.put("data","深圳市宝安区三围索佳科技园");



        Map resultMap = restTemplate.postForObject(url, requestMap, Map.class, paramMap);

        return resultMap;
    }

    @RequestMapping("/06")
    public Object consumer06(){

        /*
        RestTemplate的put方式请求，是没有返回值类型的
        参数1：请求地址的url
        参数2：请求体中封装的数据
        参数3：请求体中地址栏封装的数据，rest ful
        void put(
                    String url,
                    @Nullable Object request,
                    Map<String, ?> uriVariables)
         */
        String url = "http://EUREKA-CLIENT-PROVIDER/provider/06/{id}/{name}?age=11&data=三围索佳科技园";
//        String url = "http://localhost:7001/provider/06/{id}/{name}?age=11&data=三围索佳科技园";

        //封装的请求地址栏中的参数信息
        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("id",33);
        paramMap.put("name","tianqi");

        //封装请求体中的参数信息
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("desc","动力节点");

        restTemplate.put(url,requestMap,paramMap);

        return "success";
    }

    @RequestMapping("/07")
    public Object consumer07(){

        /*
        RestTemplate的delete方式请求，是没有返回值类型，也没有请求体
        参数1：请求地址的url
        参数3：请求体中地址栏封装的数据，rest ful
        public void delete(String url, Object... uriVariables)
         */
        String url = "http://EUREKA-CLIENT-PROVIDER/provider/07/{id}/{name}?age=22&data=索佳科技园";
//        String url = "http://localhost:7001/provider/07/{id}/{name}?age=22&data=索佳科技园";

        //封装的请求地址栏中的参数信息
        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("id",555);
        paramMap.put("name","wangba");

        restTemplate.delete(url,paramMap);

        return "success";
    }






}
