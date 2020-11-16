package cn.huimin100.tc.owf.statemachine.order.state.pay;

import cn.huimin100.tc.owf.statemachine.order.RequestContext;
import cn.huimin100.tc.owf.statemachine.order.context.OrderRequestContext;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;

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
 * @description: 支付待收款
 * @date 2020/10/14 18:09
 * @see Object
 * @since 1.0
 */
public class PayPending implements OrderStateRequest {

    private final int value = 232;

    private final String desc = "待收款";

    private OrderRequestContext context;

    @Override
    public OrderRequestContext getContext() {
        return context;
    }

    @Override
    public void setContext(RequestContext<Order> context) {
        this.context = (OrderRequestContext)context;
    }

    @Override
    public int getStateValue() {
        return value;
    }

    @Override
    public String getStateDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "PayState{" +
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
