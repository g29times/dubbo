package com.example.demo.state.order.state;

import com.example.demo.state.order.ContextApi;
import com.example.demo.state.order.context.OrderContext;
import com.example.demo.state.order.domain.Order;
import com.example.demo.state.order.experiment.processor.AbstractProcessor;

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
 * @description: 储运已接单
 * @date 2020/10/21 17:19
 * @see Object
 * @since 1.0
 */
public class LogisticsCreate extends AbstractProcessor<Order> implements OrderRequestState {

    private int value = 31;

    private final String desc = "运送中";

    private OrderContext context;

    public LogisticsCreate() {
    }

    public LogisticsCreate(ContextApi<Order> context) {
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
        return "LogisticsCreate{" +
                "value=" + value +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public void update(Order order) {
        getContext(order).setState(this);
        System.out.println(getContext(order) + " - " + order + " -> 创建储运单");
        order.setState(value);
    }

    @Override
    public void reverse(Order order) {
        System.out.println("输运拦截");
    }

    @Override
    public void next(Order order) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + getContext(order) + " - " + order + " 运送 -> 已送达");
    }

    @Override
    public void process(Order order) {
        next(order);
    }
}
