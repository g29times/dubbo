package cn.huimin100.tc.owf.statemachine.order.state.order;

import cn.huimin100.tc.owf.statemachine.order.ContextApi;
import cn.huimin100.tc.owf.statemachine.order.context.OrderContext;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.experiment.processor.AbstractProcessor;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;
import cn.huimin100.tc.owf.statemachine.order.state.enums.OrderStatusEnum;

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
 * @description: 订单取消
 * @date 2020/10/14 18:09
 * @see Object
 * @since 1.0
 */
public class OrderCancel extends AbstractProcessor<Order> implements OrderStateRequest {

    private final int value = 18;

    private final String desc = "已取消";

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
