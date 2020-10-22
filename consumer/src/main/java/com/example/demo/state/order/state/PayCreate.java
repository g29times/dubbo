package com.example.demo.state.order.state;

import com.example.demo.state.order.ContextApi;
import com.example.demo.state.order.StateApi;
import com.example.demo.state.order.context.OrderContext;
import com.example.demo.state.order.domain.Order;

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
 * @description: 用户已支付订单
 * @date 2020/10/21 17:18
 * @see Object
 * @since 1.0
 */
public class PayCreate implements OrderState {

    private int value = 21;

    private OrderContext context;

    public PayCreate(ContextApi<Order> context) {
        this.context = (OrderContext) context;
        this.context.setState(this);
    }

    @Override
    public int getStateValue() {
        return value;
    }

    @Override
    public void setStateValue(int value) {
        this.value = value;
    }

    @Override
    public ContextApi<Order> getContext() {
        return context;
    }

    @Override
    public String toString() {
        return getStateValue() + "";
    }

    @Override
    public void update(Order order) {

    }

    @Override
    public void reverse(Order order) {

    }

    @Override
    public void next(Order order) {
        StateApi<Order> next = context.getLogisticsCreate();
        getContext().setState(next);
        System.out.println("[" + Thread.currentThread().getName() + "] " + getContext() + " - " + order + " -> 订单下发到储运");
        order.setState(next.getStateValue());
    }
}
