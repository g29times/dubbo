package com.example.demo.state.order.strategy;

import com.example.demo.state.order.*;

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
 * @description: 创建流程
 * @date 2020/10/14 20:12
 * @see Object
 * @since 1.0
 */
public class OrderCreateStrategy implements StrategyApi<Order> {

    private OrderWorkFlow context;

    public OrderCreateStrategy(ContextApi<Order> context) {
        this.context = (OrderWorkFlow) context;
    }

    public OrderCreateStrategy() {
    }

    public OrderCreateStrategy(Order order) {
        System.out.println(order);
    }

    public static OrderCreateStrategy getInstance() {
        return new OrderCreateStrategy();
    }

    public static void getInstance(Order order) {
        System.out.println(order);
    }

    @Override
    public void request(Order order) {
        StateApi<Order> state = context.getCreateState();
        state.update(order);
        state.inform(order);
        state.log(order);
    }
}
