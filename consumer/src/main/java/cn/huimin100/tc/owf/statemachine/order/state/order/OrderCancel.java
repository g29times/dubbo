package cn.huimin100.tc.owf.statemachine.order.state.order;

import cn.huimin100.tc.owf.statemachine.order.RequestContext;
import cn.huimin100.tc.owf.statemachine.order.context.OrderRequestContext;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.experiment.processor.AbstractProcessor;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;
import cn.huimin100.tc.owf.statemachine.order.state.enums.LogisticsStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.OrderStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.PayStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.logistics.LogisticsPick;
import cn.huimin100.tc.owf.statemachine.order.state.pay.PayWaiting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class OrderCancel /*extends AbstractProcessor<Order> */implements OrderStateRequest {

    private final int value = 18;

    private final String desc = "已取消";

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
    public void pre(Order order) {

    }

    @Override
    public void reverse(Order order) {

    }

    @Override
    public void change(Order order) {

    }

    public List<OrderStateRequest> getParents() {
        return new ArrayList<>(Arrays.asList(OrderStatusEnum.CREATE.getState(), PayStatusEnum.WAITING.getState(), LogisticsStatusEnum.PICK.getState()));
    }

    @Override
    public boolean check(Order order) {
        return getParents().contains(order.getTypeState().get(1)) ||
                getParents().contains(order.getTypeState().get(2)) ||
                getParents().contains(order.getTypeState().get(3));
    }

}
