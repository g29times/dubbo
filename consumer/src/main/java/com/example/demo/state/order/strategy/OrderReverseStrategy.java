package com.example.demo.state.order.strategy;

import com.example.demo.state.order.ContextApi;
import com.example.demo.state.order.Order;
import com.example.demo.state.order.StrategyApi;

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
 * @description: 退货流程
 * @date 2020/10/14 20:12
 * @see Object
 * @since 1.0
 */
public class OrderReverseStrategy implements StrategyApi {

    private ContextApi context;

    public OrderReverseStrategy(ContextApi context) {
        this.context = context;
    }

    @Override
    public void request(Order order) {
        context.getState().reverse(order);
        context.getState().inform(order);
        context.getState().log(order);
    }
}
