package com.example.demo.spi.route;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.Router;
import org.apache.dubbo.rpc.cluster.RouterFactory;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.apache.dubbo.rpc.cluster.loadbalance.RandomLoadBalance;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>
 *
 * <a href="https://www.cnblogs.com/oldtrafford/p/8722790.html">一种dubbo逻辑路由方案</a><p>
 *
 * @author li tong
 * @description: SPI扩展路由示范
 * @date 2020/10/12 16:53
 * @see Object
 * @since 1.0
 */
public class DemoRouterFactory extends AbstractLoadBalance implements RouterFactory {

    private static final String NAME = "demo";

    public DemoRouterFactory() {
    }

    @Override
    public Router getRouter(URL url) {
        return new DemoRouter(url);
    }

    private static final RandomLoadBalance RANDOM_LOAD_BALANCE = new RandomLoadBalance();

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        String limitIps = getLimitIps(invocation);
        List<Invoker<T>> groupInvokerList = invokers.stream().filter(new Predicate<Invoker<T>>() {
            @Override
            public boolean test(Invoker<T> invoker) {
                return StringUtils.contains(limitIps, invoker.getUrl().getHost());
            }
        }).collect(Collectors.toList());
        return RANDOM_LOAD_BALANCE.select(groupInvokerList, url, invocation);
    }

    private String getLimitIps(Invocation invocation) {
        return "0.0.0.0";
//        Object[] args = invocation.getArguments();
//        if(args == null) {
//            logger.error("GroupLoadBalance doSelect() error, args == null!");
//            return null;
//        }
//        String limitIps = null;
//        for(Object o : args) {
//            if(!(o instanceof JobModel)) {
//                continue;
//            }
//            JobModel jm = (JobModel)o;
//            if(jm == null || StringUtils.isBlank(jm.getLimitIps())) {
//                logger.error("GroupLoadBalance doSelect() error, limitIps isBlank!");
//                return null;
//            }
//            return jm.getLimitIps();
//        }
//        return null;
    }

}
