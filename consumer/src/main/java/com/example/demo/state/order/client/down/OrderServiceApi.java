package com.example.demo.state.order.client.down;

import com.example.demo.state.order.client.observer.ApplicationContext;
import com.example.demo.state.order.client.observer.ApplicationEvent;
import com.example.demo.state.order.domain.Order;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>模拟订单服务
 * <a href="www.google.com">google</a>
 *
 * @author li tong
 * @description:
 * @date 2020/10/29 11:01
 * @see Object
 * @since 1.0
 */
public class OrderServiceApi {

    public int addOrUpdate(Order order) {
        System.out.println("OrderServiceApi addOrUpdate " + order);
        return 1;
    }

}
