package com.example.demo.controller;

import com.example.demo.api.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @DubboReference(version = "1.0.0", timeout = 60000, check = false)
    private DemoService demoService;

    @GetMapping
    @RequestMapping("/hello")
    public String hello(String name) {
        System.out.println("//Rest > /hello name:" + name);
        return "Hello" + name;
    }

    /**
     * nacos http://47.94.148.69:8848/nacos/#/serviceManagement?dataId=&group=&appName=&namespace=&pageSize=&pageNo=&serverId=
     * @param name
     * @return
     */
    @GetMapping
    @RequestMapping("/sayHello")
    public String sayHello(String name) {
        System.out.println("//Rest > /sayHello name:" + name);
        return demoService.sayHello(name);
    }
}
