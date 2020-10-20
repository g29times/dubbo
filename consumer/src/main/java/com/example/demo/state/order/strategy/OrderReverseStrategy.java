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
 * @description: 退货流程
 * @date 2020/10/14 20:12
 * @see Object
 * @since 1.0
 */
public class OrderReverseStrategy implements StrategyApi<Order> {

    private final OWFContext context;

    public OrderReverseStrategy(ContextApi<Order> context) {
        this.context = (OWFContext) context;
    }

    @Override
    public void requestStrategy(Order order) {
        StateApi<Order> state = context.getReverseState();
        // TODO 需配置化
        state.reverse(order);
        state.inform(order);
        state.log(order);
    }
}
