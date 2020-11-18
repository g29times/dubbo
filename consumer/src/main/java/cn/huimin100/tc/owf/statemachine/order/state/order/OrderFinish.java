package cn.huimin100.tc.owf.statemachine.order.state.order;

import cn.huimin100.tc.owf.statemachine.order.RequestContext;
import cn.huimin100.tc.owf.statemachine.order.context.OrderRequestContext;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.experiment.processor.AbstractProcessor;
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
 * @description: 订单完成
 * @date 2020/10/14 18:06
 * @see Object
 * @since 1.0
 */
public class OrderFinish extends AbstractProcessor<Order> implements OrderStateRequest {

    private final int value = 17;

    private final String desc = "已完成";

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
        return "OrderState{" +
                "value=" + value +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public void pre(Order order) {

    }

    @Override
    public void reverse(Order order) {

    }

    @Override
    public void change(Order order) {
        System.out.println(System.currentTimeMillis() + " [" + Thread.currentThread().getName() + "]" +
                " <" + getContext() + "> "/* + order*/ + " 已完成 没有后续节点");
    }

    @Override
    public void process(Order domain) {
        change(domain);
    }
}
