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
 * @description: 完成订单
 * @date 2020/10/14 18:06
 * @see Object
 * @since 1.0
 */
public class OrderFinishRequestState implements OrderRequestState {

    private int value = 13;

    private final String desc = "已完成";

    private OrderContext context;

    public OrderFinishRequestState() {
    }

    public OrderFinishRequestState(ContextApi<Order> context) {
        this.context = (OrderContext) context;
        this.context.setState(this);
    }

    @Override
    public int getStateValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "OrderFinishState{" +
                "value=" + value +
                ", desc='" + desc + '\'' +
                '}';
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

    @Override
    public void process(Order domain) {
        next(domain);
    }
}
