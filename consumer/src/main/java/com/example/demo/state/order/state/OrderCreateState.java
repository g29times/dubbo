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
 * @description: 创建
 * @date 2020/10/14 18:06
 * @see Object
 * @since 1.0
 */
public class OrderCreateState implements OrderState {

    private int value = 11;

    private OrderContext context;

    public OrderCreateState(ContextApi<Order> context) {
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
        getContext().setState(this);
        System.out.println(getContext() + " - " + order + " -> 更新订单"/* + getStateValue()*/);
        order.setState(value);
    }

    @Override
    public void reverse(Order order) {
        StateApi<Order> reverse = context.getOrderReverse();
        getContext().setState(reverse);
        System.out.println(getContext() + " - " + order + " -> 取消订单");
        order.setState(reverse.getStateValue());
    }

    @Override
    public void next(Order order) {
        StateApi<Order> next = context.getPayCreate();
        getContext().setState(next);
        System.out.println("[" + Thread.currentThread().getName() + "] " + getContext() + " - " + order + " -> 订单下发到支付");
        order.setState(next.getStateValue());
    }

}
