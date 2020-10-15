package com.example.demo.api.impl;

import com.example.demo.api.DubboProviderApi;
import org.apache.dubbo.config.annotation.DubboService;

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
 * @description: 服务提供方实现接口
 * @date 2020/10/12 11:15
 * @see Object
 * @since 1.0
 */
@DubboService(version = "1.0.0", interfaceClass = DubboProviderApi.class, protocol = {/*"rest", */"dubbo"})
public class DubboProviderApiImpl implements DubboProviderApi {
    @Override
    public String sayHello(String name) {
        System.out.println("Dubbo//sayHello: " + name);
        return "Name: " + name;
    }
}
