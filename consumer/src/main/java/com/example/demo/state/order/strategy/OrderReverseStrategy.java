package com.example.demo.state.order.strategy;

import com.example.demo.state.order.*;
import org.aspectj.weaver.ast.Or;

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

    private final OrderWorkFlow context;

    public OrderReverseStrategy(ContextApi<Order> context) {
        this.context = (OrderWorkFlow) context;
    }

    @Override
    public void request(Order order) {
        StateApi<Order> state = context.getReverseState();
        state.reverse(order);
        state.inform(order);
        state.log(order);
    }
}
