package com.example.demo.spi.demo;

import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>
 * <a href="http://dubbo.apache.org/zh-cn/docs/source_code_guide/dubbo-spi.html">dubbo-spi</a>
 *
 * @author li tong
 * @description:
 * @date 2020/10/13 17:25
 * @see Object
 * @since 1.0
 */
public class JavaSPITest {

    @Test
    public void sayHello() throws Exception {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(Robot::sayHello);
    }
}
