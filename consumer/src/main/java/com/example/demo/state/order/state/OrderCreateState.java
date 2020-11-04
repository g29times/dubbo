package com.example.demo.state.order.state;

import com.example.demo.state.order.StateApi;
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
 * @description: 创建订单
 * @date 2020/10/14 18:06
 * @see Object
 * @since 1.0
 */
public class OrderCreateState extends AbstractProcessor<Order> implements OrderState {

    private int value = 11;

//    private OrderContext context;

    public OrderCreateState() {
    }

//    public OrderCreateState(ContextApi<Order> context) {
//        this.context = (OrderContext) context;
//        this.context.setState(this);
//    }

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
        System.out.println(getContext() + " - " + order + " -> 创建订单");
        order.setState(value);
    }

    @Override
    public void reverse(Order order) {
        StateApi<Order> reverse = getContext().getOrderCancel();
        getContext().setState(reverse);
        System.out.println(getContext() + " - " + order + " -> 取消订单");
        order.setState(reverse.getStateValue());
    }

    @Override
    public void next(Order order) {
        // TODO 这里考虑配置化
        // V1 写死：context.getPayCreate();
        // V2 查库(或配置)获得：context.getNext();
        // V3 通过服务调用结果判断走哪个分支
        StateApi<Order> next = getContext().getPayCreate();
        getContext().setState(next);
        System.out.println("[" + Thread.currentThread().getName() + "]" +
                "[Context]" + getContext() + "[Order]" + order + "-> 订单下发到支付");
        // 仅模拟，实际是调用oms接口保存实体状态
        order.setState(next.getStateValue());
    }

    /**
     * 实现预编排
     */
    @Override
    public void process(Order order) {
        update(order);
    }

}
