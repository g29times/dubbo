package com.example.demo.state.order.state;

import com.example.demo.state.order.ContextApi;
import com.example.demo.state.order.Order;
import com.example.demo.state.order.OrderWorkFlow;

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
public class OrderFinishState extends OrderStateAbstract {

    private int value = 2;

    private final OrderWorkFlow context;

    public OrderFinishState(ContextApi<Order> context) {
        this.context = (OrderWorkFlow) context;
        this.context.setState(this);
    }

    @Override
    public int getState() {
        return value;
    }

    @Override
    public void setState(int state) {
        this.value = state;
    }

    @Override
    public ContextApi<Order> getContext() {
        return context;
    }

    @Override
    public String toString() {
        return getState() + "";
    }

}
