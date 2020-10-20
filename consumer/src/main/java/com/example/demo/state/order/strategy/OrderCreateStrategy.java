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

    private OWFContext context;

    public OrderCreateStrategy(ContextApi<Order> context) {
        this.context = (OWFContext) context;
    }

    @Override
    public void requestStrategy(Order order) {
        StateApi<Order> state = context.getCreateState();
        // TODO 需配置化
        state.update(order);
        state.inform(order);
        state.log(order);
    }





    private OrderCreateStrategy() {
    }

    private static final OrderCreateStrategy STRATEGY = new OrderCreateStrategy();

    public static OrderCreateStrategy getInstance() {
        return STRATEGY;
    }

    public static void process(Order order) {
        System.out.println(order);
        // TODO 需配置化
//        order.setState();
//        state.update(order);
//        state.inform(order);
//        state.log(order);
    }

}
