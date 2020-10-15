package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>
 * nacos http://47.94.148.69:8848/nacos/#/serviceManagement?dataId=&group=&appName=&namespace=&pageSize=&pageNo=&serverId=
 *
 * @author li tong
 * @description:
 * @date 2020/10/12 11:04
 * @see Object
 * @since 1.0
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class DemoConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoConsumerApplication.class, args);
    }
}
