package com.example.demo.state.order.state;

import com.example.demo.state.order.ContextApi;
import com.example.demo.state.order.StateApi;
import com.example.demo.state.order.client.down.PayServiceApi;
import com.example.demo.state.order.context.OrderContext;
import com.example.demo.state.order.domain.Bill;
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
 * @description: 用户已支付订单
 * @date 2020/10/21 17:18
 * @see Object
 * @since 1.0
 */
public class PayCreate extends AbstractProcessor<Order> implements OrderState {

    private int value = 21;

    public PayCreate() {
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
    public String toString() {
        return getStateValue() + "";
    }

    @Override
    public void update(Order order) {
        getContext().setState(this);
        System.out.println(getContext() + " - " + order + " -> 创建支付单");
        order.setState(value);
    }

    @Override
    public void reverse(Order order) {
        StateApi<Order> prev = getContext().getOrderCancel();
        getContext().setState(prev);
        System.out.println("[" + Thread.currentThread().getName() + "] " + getContext() + " - " + order + " -> 支付取消");
        order.setState(prev.getStateValue());
    }

    @Override
    public void next(Order order) {
//        StateApi<Order> next = context.getLogisticsCreate();
//        getContext().setState(next);
//        System.out.println("[" + Thread.currentThread().getName() + "] " + getContext() + " - " + order + " -> 支付单下发到储运");
//        order.setState(next.getStateValue());

        // 模拟调用支付系统 状态分支
        if (new PayServiceApi().addOrUpdate(new Bill())) {
            StateApi<Order> next = getContext().getLogisticsCreate();
            getContext().setState(next);
            System.out.println("[" + Thread.currentThread().getName() + "] " + getContext() + " - " + order + " -> 支付单下发到储运");
            order.setState(next.getStateValue());
        } else {
            reverse(order);
        }
    }

    @Override
    public void process(Order order) {
        update(order);
    }

}
