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
public abstract class OrderStateAbstract implements StateApi<Order> {

    /**
     * 更新状态
     */
    @Override
    public void update(Order t) {
        System.out.println(getContext() + "_" + t + "更新订单：" + getState());
        t.setState(getContext().getState());
    }

    /**
     * 逆向状态（退款）
     */
    @Override
    public void reverse(Order t) {
        System.out.println(getContext() + "_" + t + "逆向订单：" + getState());
        t.setState(getContext().getState());
    }

    /**
     * 下发
     */
    @Override
    public void inform(Order t) {
        System.out.println(getContext() + "_" + t + "订单下发：" + getState());
    }

    /**
     * 操作日志（快照）
     */
    @Override
    public void log(Order t) {
        System.out.println(getContext() + "_" + t + "操作日志：" + getState());
    }
}
