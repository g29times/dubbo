package com.example.demo.spi.loadbalance;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.apache.dubbo.rpc.cluster.loadbalance.RandomLoadBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.List;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>
 * 针对开发场景做的Dubbo的负载均衡策略，可按IP规则做路由配置。
 * IP规则例子：192.168.*.*,172.*.*.*
 * 使用逗号分隔多个正则表达式规则，如果前面的匹配则不会执行后面的，如果都不匹配则使用默认的路由规则：随机策略。
 * <a href="https://blog.csdn.net/superbible_cs/article/details/87538373">Dubbo开发环境下自定义路由规则</a>
 *
 * @author li tong
 * @description:
 * @date 2020/10/13 15:32
 * @see Object
 * @since 1.0
 */
public class DevLoadBalance extends AbstractLoadBalance {

    @Autowired
    private Environment env;

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        System.out.println(" --- DevLoadBalance --- ");
        String config = env.getProperty("dubbo.loadbalance.dev");
        // do while false @see https://blog.csdn.net/this_capslock/article/details/41843371
        do {
            if (StringUtils.isBlank(config)) {
                break;
            }
            String[] patterns = config.split(",");
            if (ArrayUtils.isEmpty(patterns)) {
                break;
            }
            for (String pattern : patterns) {
                for (Invoker<T> invoker : invokers) {
                    if (invoker.getUrl().getHost().matches(pattern)) {
                        return invoker;
                    }
                }
            }
        } while (false);
        return new RandomLoadBalance().select(invokers, url, invocation);
    }
}
