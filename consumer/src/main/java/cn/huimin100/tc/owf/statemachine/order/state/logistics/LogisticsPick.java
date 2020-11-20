package cn.huimin100.tc.owf.statemachine.order.state.logistics;

import cn.huimin100.tc.owf.statemachine.order.RequestContext;
import cn.huimin100.tc.owf.statemachine.order.StateRequest;
import cn.huimin100.tc.owf.statemachine.order.context.OrderRequestContext;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.state.enums.LogisticsStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.OrderStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;
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
 * @description: 仓配已拣货
 * @date 2020/10/21 17:19
 * @see Object
 * @since 1.0
 */
public class LogisticsPick implements OrderStateRequest {

    private final int value = 32;

    private final String desc = "已拣货";

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
        return "LogisticsState{" +
                "value=" + value +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public Boolean isHandler(Order order) {
        Map<Integer, OrderStateRequest> typeState = order.getTypeState();
        return typeState.get(1).getStateValue() == OrderStatusEnum.CREATE.getCode()
                && typeState.get(2).getStateValue() == PayStatusEnum.WAITING.getCode()
                && typeState.get(3).getStateValue() == LogisticsStatusEnum.PICK.getCode();
    }

    @Override
    public void pre(Order order) {
        System.out.println(getContext() + " - " + order + " -> 已拣货");
    }

    @Override
    public void reverse(Order order) {
        System.out.println("输运拦截");
    }

    @Override
    public void change(Order order) {
        StateRequest<Order> next = OrderStatusEnum.FINISH.getState();
        getContext().setState(next);
        System.out.println(System.currentTimeMillis() + " [" + Thread.currentThread().getName() + "]" +
                " <" + getContext() + "> "/* + order*/ + " 已拣货 -> 已送达（已完成）");
        order.setState1(next.getStateValue());
    }

}
