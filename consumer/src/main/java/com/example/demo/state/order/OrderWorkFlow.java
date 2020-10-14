package com.example.demo.state.order;

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
 * @description:
 * @date 2020/10/14 17:04
 * @see Object
 * @since 1.0
 */
public class OrderWorkFlow implements ContextApi {
    @Override
    public String toString() {
        return "OrderFlowContext{" +
                "state=" + state +
                '}';
    }

    @Override
    public ContextApi getContext() {
        return this;
    }

    /**
     * 持有一个State类型的对象实例
     */
    private StateApi state;
    /**
     * 持有一个Strategy类型的对象实例
     */
    private StrategyApi strategy;

    @Override
    public StateApi getState() {
        return state;
    }

    @Override
    public void setState(StateApi state) {
        this.state = state;
    }

    @Override
    public StrategyApi getStrategy() {
        return strategy;
    }

    @Override
    public void setStrategy(StrategyApi strategy) {
        this.strategy = strategy;
    }

    /**
     * 订单流程编排
     */
    @Override
    public void request(Order order) {
//        // 转调state来处理
//        state.update(order);
//        state.inform(order);
//        state.reverse(order);
//        state.log(order);
        // 转调strategy来处理
        strategy.request(order);
    }
}
