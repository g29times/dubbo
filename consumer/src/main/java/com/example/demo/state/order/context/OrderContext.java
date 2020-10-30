package com.example.demo.state.order.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.example.demo.state.order.ContextApi;
import com.example.demo.state.order.StateApi;
import com.example.demo.state.order.StrategyApi;
import com.example.demo.state.order.client.observer.ApplicationContext;
import com.example.demo.state.order.client.observer.OrderObserver;
import com.example.demo.state.order.domain.Order;
import com.example.demo.state.order.state.*;

import java.lang.ref.SoftReference;

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
public class OrderContext implements ContextApi<Order> {

    private static final TransmittableThreadLocal<SoftReference<OrderContext>> CONTEXT = new TransmittableThreadLocal<>();

    public OrderContext() {
        // 初始化上下文
        System.out.println(" --- OrderContext is created --- ");
        SoftReference<OrderContext> sf = new SoftReference<>(this);
        CONTEXT.set(sf);
        // 创建状态集
        this.orderCreate = new OrderCreateState(this);
        this.orderFinish = new OrderFinishState(this);
        this.orderCancel = new OrderCancelState(this);
        this.payCreate = new PayCreate(this);
        this.logisticsCreate = new LogisticsCreate(this);
        // 设置初始状态
        initState();
        // 开始监听
        OrderObserver.listen();
    }

    public static OrderContext getOrderContext() {
        return CONTEXT.get().get();
    }

    public static void restoreContext(OrderContext oldContext) {
        CONTEXT.set(new SoftReference<>(oldContext));
    }

    public static void removeContext() {
        System.out.println("removeContext");
        CONTEXT.remove();
    }

    private Order domain;

    @Override
    public ContextApi<Order> of(Order domain) {
        if (domain.getState() == 0) {
            domain.setState(this.orderCreate.getStateValue());
//        db.insert(domain);
            System.out.println(getOrderContext() + " - " + "订单已创建 " + domain);
        }
        setDomain(domain);
        return this;
    }

    /**
     * 持有一个State类型的对象实例
     */
    private OrderState state;

    private void initState() {
        this.state = this.orderCreate;
    }

    /**
     * 定义出所有状态
     */
    private final OrderState orderCreate;
    private final OrderState orderFinish;
    private final OrderState orderCancel;
    private final OrderState payCreate;
    private final OrderState logisticsCreate;

    /**
     * 持有一个Strategy类型的对象实例
     */
    private StrategyApi strategy;

    // *************************** 基础方法区

    public OrderState getOrderCreate() {
        return orderCreate;
    }

    public OrderState getOrderFinish() {
        return orderFinish;
    }

    public OrderState getOrderCancel() {
        return orderCancel;
    }

    public OrderState getPayCreate() {
        return payCreate;
    }

    public OrderState getLogisticsCreate() {
        return logisticsCreate;
    }

    @Override
    public Order getDomain() {
        return domain;
    }

    @Override
    public ContextApi setDomain(Order domain) {
        this.domain = domain;
        return this;
    }

    @Override
    public StateApi getState() {
        return (StateApi) state;
    }

    @Override
    public ContextApi setState(StateApi state) {
        this.state = (OrderState) state;
        return this;
    }

    @Override
    public StrategyApi getStrategy() {
        return strategy;
    }

    @Override
    public ContextApi setStrategy(StrategyApi strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public String toString() {
        return OrderContext.class.getSimpleName();
    }

    // *************************** 业务方法区

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

    public void update(Order order) {
        state.update(order);
    }

    public void reverse(Order order) {
        state.reverse(order);
    }

//    @Override
    public void next(Order order) {
        state.next(order);
    }

}
