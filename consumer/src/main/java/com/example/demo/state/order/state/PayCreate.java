package com.example.demo.state.order.state;

import com.example.demo.state.order.ContextApi;
import com.example.demo.state.order.StateRequest;
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
public class PayCreate extends AbstractProcessor<Order> implements OrderStateRequest {

    private final int value = 21;

    private final String desc = "已支付";

    private OrderContext context;

    @Override
    public OrderContext getContext() {
        return context;
    }

    @Override
    public void setContext(ContextApi<Order> context) {
        this.context = (OrderContext)context;
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
        return "PayCreate{" +
                "value=" + value +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public void update(Order order) {
        getContext().setState(this);
        System.out.println(getContext() + " - " + order + " -> 创建支付单");
        order.setState(value);
    }

    @Override
    public void reverse(Order order) {
        StateRequest<Order> prev = OrderStatusEnum.CANCLE.getState();
        getContext().setState(prev);
        System.out.println(System.currentTimeMillis() + " [" + Thread.currentThread().getName() + "]" +
                " <" + getContext() + "> " + order + " -> 支付取消");
        order.setState(prev.getStateValue());
    }

    @Override
    public void next(Order order) {
        if (order.getState().equals(OrderStatusEnum.CANCLE.getState().getStateValue())) {
            System.out.println("支付已取消 无法继续！");
            return;
        }
        if (!order.getState().equals(OrderStatusEnum.PAY.getState().getStateValue())) {
            System.out.println("支付状态跳号 无法继续！期望值：" + OrderStatusEnum.PAY.getState() + "，实际值：" + order.getState());
            return;
        }
        // TODO 模拟调用支付系统 状态分支
        if (new PayServiceApi().addOrUpdate(new Bill())) {
            StateRequest<Order> next = OrderStatusEnum.LOGISTICS.getState();
            getContext().setState(next);
            System.out.println(System.currentTimeMillis() + " [" + Thread.currentThread().getName() + "]" +
                    " <" + getContext() + "> " + order + " 已支付 -> 运送中");
            order.setState(next.getStateValue());
        } else {
            reverse(order);
        }
    }

    @Override
    public void process(Order order) {
        next(order);
    }

}
