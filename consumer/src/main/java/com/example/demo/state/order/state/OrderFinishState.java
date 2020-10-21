package com.example.demo.state.order.state;

import com.example.demo.state.order.ContextApi;
import com.example.demo.state.order.domain.Order;
import com.example.demo.state.order.context.OrderContext;

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
 * @description: 完成
 * @date 2020/10/14 18:06
 * @see Object
 * @since 1.0
 */
public class OrderFinishState implements OrderState {

    private int value = 13;

    private OrderContext context;

    public OrderFinishState(ContextApi<Order> context) {
        this.context = (OrderContext) context;
        this.context.setState(this);
    }

    @Override
    public int getStateValue() {
        return value;
    }

    @Override
    public void setStateValue(int state) {
        this.value = state;
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

    }
}
