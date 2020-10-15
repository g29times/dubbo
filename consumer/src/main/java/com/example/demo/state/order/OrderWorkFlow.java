package com.example.demo.state.order;

import com.example.demo.state.order.state.OrderCreateState;
import com.example.demo.state.order.state.OrderFinishState;
import com.example.demo.state.order.state.OrderReverseState;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * 订单流程驱动
 * <a href="www.google.com">google</a>
 *
 * @author li tong
 * @description:
 * @date 2020/10/14 17:04
 * @see Object
 * @since 1.0
 */
public class OrderWorkFlow implements ContextApi<Order> {

    @Override
    public String toString() {
        return "OrderWorkFlow";
    }

    /**
     * 定义出所有状态
     */
    private final StateApi<Order> createState;
    private final StateApi<Order> finishState;
    private final StateApi<Order> reverseState;

    public OrderWorkFlow() {
        this.createState = new OrderCreateState(this);
        this.finishState = new OrderFinishState(this);
        this.reverseState = new OrderReverseState(this);
    }

    public StateApi<Order> getCreateState() {
        return createState;
    }

    public StateApi<Order> getFinishState() {
        return finishState;
    }

    public StateApi<Order> getReverseState() {
        return reverseState;
    }

    /**
     * 持有一个State类型的对象实例
     */
    private StateApi<Order> state;

    /**
     * 持有一个Strategy类型的对象实例
     */
    private StrategyApi strategy;

    @Override
    public StateApi<Order> getState() {
        return state;
    }

    @Override
    public void setState(StateApi<Order> state) {
        this.state = state;
    }

    @Override
    public ContextApi<Order> setStrategy(StrategyApi strategy) {
        this.strategy = strategy;
        return this;
    }

    /**
     * 订单流程编排
     */
    @Override
    public void request(Order order) {
//        // 转调state来处理 状态模式 - 状态可扩展
//        state.update(order);
//        state.inform(order);
//        state.reverse(order);
//        state.log(order);
        // 再次转调strategy来处理 策略模式 - 行为可配置
        strategy.request(order);
    }
}
