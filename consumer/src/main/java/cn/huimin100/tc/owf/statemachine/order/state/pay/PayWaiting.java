package cn.huimin100.tc.owf.statemachine.order.state.pay;

import cn.huimin100.tc.owf.statemachine.order.StateRequest;
import cn.huimin100.tc.owf.client.PayServiceApi;
import cn.huimin100.tc.owf.statemachine.order.domain.Bill;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.experiment.processor.AbstractProcessor;
import cn.huimin100.tc.owf.statemachine.order.ContextApi;
import cn.huimin100.tc.owf.statemachine.order.context.OrderContext;
import cn.huimin100.tc.owf.statemachine.order.state.enums.LogisticsStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.OrderStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;
import cn.huimin100.tc.owf.statemachine.order.state.enums.PayStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.StateTypeEnum;

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
public class PayWaiting extends AbstractProcessor<Order> implements OrderStateRequest {

    private final int value = 21;

    private final String desc = "待支付";

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
        return "PayState{" +
                "value=" + value +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public void update(Order order) {
        getContext().setState(this);
        System.out.println(getContext() + " - " + order + " -> 待支付");
        order.setState(value);
    }

    @Override
    public void reverse(Order order) {
        StateRequest<Order> prev = PayStatusEnum.CANCEL.getState();
        getContext().setState(prev);
        System.out.println(System.currentTimeMillis() + " [" + Thread.currentThread().getName() + "]" +
                " <" + getContext() + "> "/* + order*/ + " -> 支付取消");

        Map<Integer, StateRequest<Order>> map = order.getTypeState();
        map.put(2, prev);
        order.setTypeState(map);
//        order.setState(prev.getStateValue());
    }

    @Override
    public void next(Order order) {
        if (order.getTypeState() != null && order.getTypeState().get(1).getStateValue() == OrderStatusEnum.CANCEL.getState().getStateValue()
                /*order.getState().equals(OrderStatusEnum.CANCEL.getState().getStateValue())*/) {
            System.out.println("支付已取消 无法继续！");
            return;
        }
        if (order.getTypeState() != null && order.getTypeState().get(2).getStateValue() != PayStatusEnum.WAITING.getState().getStateValue()
            /*!order.getState().equals(PayStatusEnum.WAITING.getState().getStateValue())*/) {
            System.out.println("支付状态跳号 无法继续！期望值：" + PayStatusEnum.WAITING.getState() + "，实际值：" + order.getTypeState().get(2).getStateValue()/*order.getState()*/);
            return;
        }
        // TODO 模拟调用支付系统 状态分支
        if (new PayServiceApi().addOrUpdate(new Bill())) {
            StateRequest<Order> next = LogisticsStatusEnum.PICK.getState();
            getContext().setState(next);
            System.out.println(System.currentTimeMillis() + " [" + Thread.currentThread().getName() + "]" +
                    " <" + getContext() + "> " /*+ order */+ " 已支付 -> 运送中");

            Map<Integer, StateRequest<Order>> map = order.getTypeState();
            map.put(3, next);
            order.setTypeState(map);
//            order.setState(next.getStateValue());
//            order.setStateType(StateTypeEnum.LOGISTICS.getCode());
        } else {
            reverse(order);
        }
    }

    @Override
    public void process(Order order) {
        next(order);
    }

}
