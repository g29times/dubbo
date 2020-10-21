package com.example.demo.state.order.strategy;

import com.example.demo.state.order.*;
import com.example.demo.state.order.context.OrderContext;
import com.example.demo.state.order.domain.Order;
import com.example.demo.state.order.state.OrderState;

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

    private OrderContext context;

    public OrderReverseStrategy(ContextApi<Order> context) {
        this.context = (OrderContext) context;
    }

    @Override
    public void process(Order order) {
        OrderState state = context.getOrderReverse();
        // TODO 需配置化
        state.reverse(order);
        state.next(order);
        state.log(order);
    }
}
