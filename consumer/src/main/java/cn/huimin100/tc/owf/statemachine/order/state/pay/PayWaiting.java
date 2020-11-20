package cn.huimin100.tc.owf.statemachine.order.state.pay;

import cn.huimin100.tc.owf.client.PayServiceApi;
import cn.huimin100.tc.owf.statemachine.order.RequestContext;
import cn.huimin100.tc.owf.statemachine.order.StateRequest;
import cn.huimin100.tc.owf.statemachine.order.context.OrderRequestContext;
import cn.huimin100.tc.owf.statemachine.order.domain.Bill;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;
import cn.huimin100.tc.owf.statemachine.order.state.enums.LogisticsStatusEnum;
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
 * @description: 待支付
 * @date 2020/10/21 17:18
 * @see Object
 * @since 1.0
 */
public class PayWaiting implements OrderStateRequest {

    private final int value = 21;

    private final String desc = "待支付";

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
        return "PayState{" +
                "value=" + value +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public Boolean isHandler(Order order) {
        Map<Integer, OrderStateRequest> typeState = order.getTypeState();
        return typeState.get(1).getStateValue() == OrderStatusEnum.CREATE.getCode()
                && typeState.get(2).getStateValue() == PayStatusEnum.WAITING.getCode() && typeState.get(3) == null;
    }

    @Override
    public void reverse(Order order) {
        StateRequest<Order> prev = PayStatusEnum.CANCEL.getState();
        getContext().setState(prev);
        System.out.println(System.currentTimeMillis() + " [" + Thread.currentThread().getName() + "]" +
                " <" + getContext() + "> "/* + order*/ + " -> 支付取消");
        order.setState2(prev.getStateValue());
    }

    @Override
    public void pre(Order order) {
        System.out.println(getContext() + " - " + order + " -> 待支付");
    }

    @Override
    public void change(Order order) {
        if (order.getTypeState() != null
                && order.getTypeState().get(1).getStateValue() == OrderStatusEnum.CANCEL.getState().getStateValue()
            /*order.getState().equals(OrderStatusEnum.CANCEL.getState().getStateValue())*/) {
            System.out.println("支付已取消 无法继续！");
            return;
        }
        if (order.getTypeState() != null
                && order.getTypeState().get(2).getStateValue() != PayStatusEnum.WAITING.getState().getStateValue()
        ) {
            System.out.println("支付状态跳号 无法继续！期望值：" + PayStatusEnum.WAITING.getState() + "，实际值："
                    + order.getTypeState().get(2).getStateValue());
            return;
        }
        // TODO 模拟调用支付系统 状态分支
        if (new PayServiceApi().addOrUpdate(new Bill())) {
            StateRequest<Order> next = LogisticsStatusEnum.PICK.getState();
            getContext().setState(next);
            System.out.println(System.currentTimeMillis() + " [" + Thread.currentThread().getName() + "]" +
                    " <" + getContext() + "> " /*+ order */ + " 已支付 -> 运送中");
            order.setState3(next.getStateValue());
        } else {
            reverse(order);
        }
    }

}
