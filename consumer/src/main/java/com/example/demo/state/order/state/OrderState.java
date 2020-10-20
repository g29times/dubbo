package com.example.demo.state.order.state;

import com.example.demo.state.order.Order;
import com.example.demo.state.order.StateApi;

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
 * @date 2020/10/15 18:38
 * @see Object
 * @since 1.0
 */
public interface OrderState extends StateApi<Order> {

    /**
     * 更新状态
     */
//    @Override
    default void update(Order order) {
        getContext().setState(this);
        System.out.println(getContext() + " - " + order + " 更新订单：" + getStateValue());
        order.setState(getContext().getState().getStateValue());
    }

    /**
     * 逆向状态（退款）
     */
//    @Override
    default void reverse(Order order) {
        System.out.println(getContext() + " - " + order + " 逆向订单：" + getStateValue());
        order.setState(getContext().getState().getStateValue());
    }

    /**
     * 下发
     */
//    @Override
    default void inform(Order order) {
        System.out.println(getContext() + " - " + order + " 订单下发：" + getStateValue());
    }

    /**
     * 操作日志（快照）
     */
//    @Override
    default void log(Order order) {
        System.out.println(getContext() + " - " + order + " 操作日志：" + getStateValue());
    }
}
