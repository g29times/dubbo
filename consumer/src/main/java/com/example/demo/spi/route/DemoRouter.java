package com.example.demo.spi.route;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.router.AbstractRouter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
 * @date 2020/10/12 19:41
 * @see Object
 * @since 1.0
 */
public class DemoRouter extends AbstractRouter {

    private boolean enabled;

    public DemoRouter(URL url) {
        this.url = url;
        this.enabled = url.getParameter("enabled", true);
    }

    @Override
    public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
        if (!this.enabled) {
            return invokers;
        } else if (CollectionUtils.isEmpty(invokers)) {
            return invokers;
        } else {
            List<Invoker<T>> result = new ArrayList();
            Iterator iterator = invokers.iterator();
            while (iterator.hasNext()) {
                Invoker<T> invoker = (Invoker) iterator.next();
//                if (this.matchThen(invoker.getUrl(), url)) {
//                    result.add(invoker);
//                }
            }
            return null;
        }
    }
}
