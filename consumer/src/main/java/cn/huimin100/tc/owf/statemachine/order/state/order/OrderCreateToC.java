package cn.huimin100.tc.owf.statemachine.order.state.order;

import cn.huimin100.tc.owf.statemachine.order.RequestContext;
import cn.huimin100.tc.owf.statemachine.order.StateRequest;
import cn.huimin100.tc.owf.statemachine.order.context.OrderRequestContext;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;
import cn.huimin100.tc.owf.statemachine.order.state.enums.OrderStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.PayStatusEnum;

import java.util.Map;

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
 * @description: ToC订单已创建
 * @date 2020/10/14 18:06
 * @see Object
 * @since 1.0
 */
public class OrderCreateToC implements OrderStateRequest {

    private final int value = 10;

    private final String desc = "已创建";

    private OrderRequestContext context;

    @Override
    public OrderRequestContext getContext() {
        return context;
    }

    @Override
    public void setContext(RequestContext<Order> context) {
        this.context = (OrderRequestContext) context;
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
    public Boolean isHandler(Order order) {
        Map<Integer, OrderStateRequest> typeState = order.getTypeState();
        return order.getBusinessLine() == 2 && (typeState.get(1) == null
                || typeState.get(1).getStateValue() == OrderStatusEnum.CREATE_TOC.getCode())
                && typeState.get(2) == null && typeState.get(3) == null;
    }

    @Override
    public void reverse(Order order) {
        StateRequest<Order> prev = OrderStatusEnum.CANCEL.getState();
        getContext().setState(prev);
        System.out.println(getContext() + " - " + order + " -> ToC取消订单");
        order.setState1(prev.getStateValue());
    }

    @Override
    public void pre(Order order) {
        System.out.println(getContext() + " - " + order + " -> ToC创建订单");
    }

    @Override
    public void change(Order order) {
        StateRequest<Order> next = PayStatusEnum.WAITING.getState();
        OrderRequestContext context = getContext();
        context.setState(next);
        System.out.println(System.currentTimeMillis() + " [" + Thread.currentThread().getName() + "]" +
                " <" + context + "> "/* + order*/ + " ToC已创建 -> 待支付");
        order.setState2(next.getStateValue());
    }

}
