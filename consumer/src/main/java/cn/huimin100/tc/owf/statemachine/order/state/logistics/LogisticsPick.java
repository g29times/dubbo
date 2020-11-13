package cn.huimin100.tc.owf.statemachine.order.state.logistics;

import cn.huimin100.tc.owf.statemachine.order.ContextApi;
import cn.huimin100.tc.owf.statemachine.order.StateRequest;
import cn.huimin100.tc.owf.statemachine.order.context.OrderContext;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.experiment.processor.AbstractProcessor;
import cn.huimin100.tc.owf.statemachine.order.state.enums.LogisticsStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.OrderStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;
import cn.huimin100.tc.owf.statemachine.order.state.enums.StateTypeEnum;

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
public class LogisticsPick extends AbstractProcessor<Order> implements OrderStateRequest {

    private final int value = 32;

    private final String desc = "已拣货";

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
        return "LogisticsState{" +
                "value=" + value +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public void update(Order order) {
        getContext().setState(this);
        System.out.println(getContext() + " - " + order + " -> 已拣货");
        order.setState(value);
    }

    @Override
    public void reverse(Order order) {
        System.out.println("输运拦截");
    }

    @Override
    public void next(Order order) {
        StateRequest<Order> next = OrderStatusEnum.FINISH.getState();
        getContext().setState(next);
        System.out.println(System.currentTimeMillis() + " [" + Thread.currentThread().getName() + "]" +
                " <" + getContext() + "> " + order + " 已拣货 -> 已送达（已完成）");
        order.setState(next.getStateValue());
        order.setStateType(StateTypeEnum.ORDER.getCode());
    }

    @Override
    public void process(Order order) {
        next(order);
    }
}
