package com.example.demo.controller;

import com.example.demo.api.DubboProviderApi;
import com.example.demo.api.FeignClientApi;
import com.example.demo.api.UserDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>
 * <a href="www.google.com">google</a>
 *
 * @author li tong
 * @description:
 * @date 2020/10/12 11:28
 * @see Object
 * @since 1.0
 */
@RestController
public class HelloController {

    @GetMapping("/ok")
    public String ok() {
        System.out.println("ok");
        return "this is client";
    }

    @DubboReference(version = "1.0.0", timeout = 60000, check = false)
    private DubboProviderApi dubboProviderApi;

    /**
     * nacos http://47.94.148.69:8848/nacos/#/serviceManagement?dataId=&group=&appName=&namespace=&pageSize=&pageNo=&serverId=
     * http://localhost:8082/dubboHello?name=1
     */
    @GetMapping("/dubboHello")
    public String dubboHello(String name) {
        System.out.println("Client > dubbo, name=" + name);
        return dubboProviderApi.sayHello(name);
    }

    @Autowired
    private FeignClientApi feignClientApi;

    /**
     * http://localhost:8082/feignHello?name=1
     */
    @GetMapping("/feignHello")
    public String feignHello(String name) {
        System.out.println("Client > feign, name=" + name);
        return feignClientApi.hello(name);
    }

    /**
     * http://localhost:8082/feignUser?id=1&name=%E4%B8%89
     */
    @GetMapping("/feignUser")
    public String feignUser(Long id, String name) {
        UserDTO user = new UserDTO(id, name);
        System.out.println("Client > feign, user=" + user);
        return feignClientApi.user(user);
    }
}
