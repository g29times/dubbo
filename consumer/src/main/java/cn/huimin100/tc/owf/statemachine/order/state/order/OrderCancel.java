package cn.huimin100.tc.owf.statemachine.order.state.order;

import cn.huimin100.tc.owf.statemachine.order.RequestContext;
import cn.huimin100.tc.owf.statemachine.order.context.OrderRequestContext;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;
import cn.huimin100.tc.owf.statemachine.order.state.enums.LogisticsStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.OrderStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.PayStatusEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 * @description: 订单取消
 * @date 2020/10/14 18:09
 * @see Object
 * @since 1.0
 */
public class OrderCancel implements OrderStateRequest {

    private final int value = 18;

    private final String desc = "已取消";

    private OrderRequestContext context;

    private final ArrayList<OrderStateRequest> parents = new ArrayList<>(Arrays.asList(
            OrderStatusEnum.CREATE.getState(),
            PayStatusEnum.WAITING.getState(),
            LogisticsStatusEnum.PICK.getState()));

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
        return typeState.get(1) != null && typeState.get(1).getStateValue() == OrderStatusEnum.CANCEL.getCode();
    }

    @Override
    public void reverse(Order order) {}

    @Override
    public boolean check(Order order) {
        return getParents().contains(order.getTypeState().get(1)) ||
                getParents().contains(order.getTypeState().get(2)) ||
                getParents().contains(order.getTypeState().get(3));
    }

    @Override
    public List<OrderStateRequest> getParents() {
        return parents;
    }

    @Override
    public void pre(Order order) {}

    @Override
    public void change(Order order) {
        System.out.println(getContext() + " - " + order + " -> 订单取消");
    }

}
