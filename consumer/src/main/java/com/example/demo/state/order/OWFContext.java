package com.example.demo.state.order;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.example.demo.state.order.state.OrderCreateState;
import com.example.demo.state.order.state.OrderFinishState;
import com.example.demo.state.order.state.OrderReverseState;
import com.example.demo.state.order.state.OrderState;

import java.lang.ref.Cleaner;

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
 * @see org.apache.dubbo.rpc.RpcContext
 * @since 1.0
 */
public class OWFContext implements ContextApi<Order>, AutoCloseable {

    /**
     * https://blog.csdn.net/u011695358/article/details/78860410
     * https://baijiahao.baidu.com/s?id=1655232869611610920&wfr=spider&for=pc
     * https://zhuanlan.zhihu.com/p/25698745
     */
    private static final Cleaner cleaner = Cleaner.create();
    private final Cleaner.Cleanable cleanable;

    private final State statex;
    static class State implements Runnable {
        State() {
            System.out.println("OWFContext init");// initialize State needed for cleaning action
        }
        public void run() {
            System.out.println("OWFContext clean");// cleanup action accessing State, executed at most once
        }
    }

    @Override
    public void close() throws Exception {
        System.out.println("OWFContext close");
        cleanable.clean();
    }

    private static final TransmittableThreadLocal<OWFContext> CONTEXT = new TransmittableThreadLocal<>();

    public static OWFContext getContext() {
        return CONTEXT.get();
    }

    public static void restoreContext(OWFContext oldContext) {
        CONTEXT.set(oldContext);
    }

    public static void removeContext() {
        CONTEXT.remove();
    }




    private Order domain;

    /**
     * 定义出所有状态
     */
    private final OrderState createState;
    private final OrderState finishState;
    private final OrderState reverseState;

    public OWFContext() {
        CONTEXT.set(this);
        this.statex = new State();
        this.cleanable = cleaner.register(this, /*statex*/ OWFContext::removeContext);

        this.createState = new OrderCreateState(this);
        this.finishState = new OrderFinishState(this);
        this.reverseState = new OrderReverseState(this);
    }

    public OrderState getCreateState() {
        return createState;
    }

    public OrderState getFinishState() {
        return finishState;
    }

    public OrderState getReverseState() {
        return reverseState;
    }

    /**
     * 持有一个State类型的对象实例
     */
    private StateApi<Order> state;

    /**
     * 持有一个Strategy类型的对象实例
     */
    private StrategyApi<Order> strategy;

    @Override
    public Order getDomain() {
        return domain;
    }

    @Override
    public ContextApi<Order> setDomain(Order domain) {
        this.domain = domain;
        return this;
    }

    @Override
    public StateApi<Order> getState() {
        return state;
    }

    @Override
    public ContextApi<Order> setState(StateApi<Order> state) {
        this.state = state;
        return this;
    }

    @Override
    public StrategyApi<Order> getStrategy() {
        return strategy;
    }

    @Override
    public ContextApi<Order> setStrategy(StrategyApi<Order> strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public String toString() {
        return "OrderWorkFlow";
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
        strategy.process(order);
    }
}
