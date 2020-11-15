package cn.huimin100.tc.owf.statemachine.order.state.order;

import cn.huimin100.tc.owf.statemachine.order.ContextApi;
import cn.huimin100.tc.owf.statemachine.order.StateRequest;
import cn.huimin100.tc.owf.statemachine.order.context.OrderContext;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.experiment.processor.AbstractProcessor;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;
import cn.huimin100.tc.owf.statemachine.order.state.enums.OrderStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.PayStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.StateTypeEnum;

import java.util.HashMap;
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
 * @description: 订单已创建
 * @date 2020/10/14 18:06
 * @see Object
 * @since 1.0
 */
public class OrderCreate extends AbstractProcessor<Order> implements OrderStateRequest {

    private final int value = 11;

    private final String desc = "已创建";

    private OrderContext context;

    @Override
    public OrderContext getContext() {
        return context;
    }

    @Override
    public void setContext(ContextApi<Order> context) {
        this.context = (OrderContext) context;
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
        getContext().setState(this);
        System.out.println(getContext() + " - " + order + " -> 创建订单");
        order.setState(value);
    }

    @Override
    public void reverse(Order order) {
        StateRequest<Order> prev = OrderStatusEnum.CANCEL.getState();
        getContext().setState(prev);
        System.out.println(getContext() + " - " + order + " -> 取消订单");

        Map<Integer, StateRequest<Order>> map = order.getTypeState();
        map.put(1, prev);
        order.setTypeState(map);
//        order.setState(prev.getStateValue());
    }

    @Override
    public /*synchronized*/ void next(Order order) {
        // TODO 这里考虑配置化
        // V1 写死：context.getPayCreate();
        // V2 查库(或配置)获得：context.getNext();
        // V3 通过服务调用结果判断走哪个分支
        StateRequest<Order> next = PayStatusEnum.WAITING.getState();
        OrderContext context = getContext();
        context.setState(next);
        System.out.println(System.currentTimeMillis() + " [" + Thread.currentThread().getName() + "]" +
                " <" + context + "> "/* + order*/ + " 已创建 -> 待支付");

        // 仅模拟，实际是调用oms接口保存实体状态
        Map<Integer, StateRequest<Order>> map = order.getTypeState();
        map.put(2, next);
        order.setTypeState(map);
//        order.setState(next.getStateValue());
//        order.setStateType(StateTypeEnum.PAY.getCode());
    }

    /**
     * 实现预编排
     */
    @Override
    public void process(Order order) {
        // 1 验证 - 2 处理 - 3 变更状态 - 4 通知
        next(order);
    }

}
