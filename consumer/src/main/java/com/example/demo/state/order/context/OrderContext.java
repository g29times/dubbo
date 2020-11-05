package com.example.demo.state.order.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.example.demo.state.order.*;
import com.example.demo.state.order.client.observer.OrderObserver;
import com.example.demo.state.order.domain.Order;
import com.example.demo.state.order.state.OrderState;
import com.example.demo.state.order.state.OrderStatusEnum;

import java.lang.ref.WeakReference;

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
    /**
     * 3
     */
    private static final class Singleton {
        private static final OrderContext INSTANCE = new OrderContext();
    }
    /**
     * 2
     */
    private static final TransmittableThreadLocal<OrderContext> THREAD_CONTEXT =
            new TransmittableThreadLocal<OrderContext>() {
                @Override
                protected OrderContext initialValue() {
                    return new OrderContext();
                }
            };
    /**
     * 1
     */
    private static final TransmittableThreadLocal<WeakReference<OrderContext>> WEAK_CONTEXT =
            new TransmittableThreadLocal<WeakReference<OrderContext>>() {
                @Override
                protected WeakReference<OrderContext> initialValue() {
                    return new WeakReference<>(new OrderContext());
                }
            };

    private OrderContext() {
        // 初始化上下文
        System.out.println(Thread.currentThread() + " --- OrderContext is created! --- " + this.hashCode());
        // 创建状态集
        this.orderCreate = OrderStatusEnum.CREATE.getState();
        this.orderFinish = OrderStatusEnum.FINISH.getState();
        this.orderCancel = OrderStatusEnum.CANCLE.getState();
        this.payCreate = OrderStatusEnum.PAY.getState();
        this.logisticsCreate = OrderStatusEnum.LOGISTICS.getState();
        // 开始监听
        OrderObserver.listen();
    }

    @Override
    protected void finalize() throws Throwable {
        removeContext();
        super.finalize();
        System.out.println("Context is disposed!");
    }

    public static OrderContext getInstance() {
        return Singleton.INSTANCE;
    }

    public static OrderContext getThreadContext() {
        return THREAD_CONTEXT.get();
    }

    public static OrderContext getWeakContext() {
        return WEAK_CONTEXT.get().get();
    }

    public static void restoreContext(OrderContext oldContext) {
        THREAD_CONTEXT.set(oldContext);
        WEAK_CONTEXT.set(new WeakReference<>(oldContext));
    }

    private static void removeContext() {
        System.out.println("removeContext");
        THREAD_CONTEXT.remove();
        WEAK_CONTEXT.remove();
    }

    // ************************************** 基础属性区

    private Order domain;

    @Override
    public ContextApi<Order> of(Order domain) {
        if (domain.getState() == null || domain.getState() == 0) {
            // 设置初始状态
            this.state = this.orderCreate;
            domain.setState(this.orderCreate.getStateValue());
//        db.insert(domain);
            System.out.println(this + " - " + "订单已创建 " + domain);
        } else {
            this.state = OrderStatusEnum.get(domain.getState());
        }
        setDomain(domain);
        return this;
    }

    /**
     * 状态 持有一个State类型的对象实例
     */
    private OrderState state;

    /**
     * 状态 定义出所有状态
     */
    private final OrderState orderCreate;
    private final OrderState orderFinish;
    private final OrderState orderCancel;
    private final OrderState payCreate;
    private final OrderState logisticsCreate;

    /**
     * 策略 持有一个Strategy类型的对象实例
     */
    private Strategy strategy;

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
    public Long getDomainId() {
        return domain.getId();
    }

    @Override
    public ContextApi setDomain(Order domain) {
        this.domain = domain;
        return this;
    }

    @Override
    public StateApi getState() {
        return state;
    }

    @Override
    public ContextApi setState(StateApi state) {
        this.state = (OrderState) state;
        return this;
    }

    @Override
    public Strategy getStrategy() {
        return strategy;
    }

    @Override
    public ContextApi setStrategy(Strategy strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public String toString() {
        return this.hashCode() + "";
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

    public void next(Order order) {
        state.next(order);
    }

    private final RequestAsyncProcessService asyncProcessor = new OrderRequestAsyncProcessServiceImpl();

    @Override
    public RequestAsyncProcessService getAsyncProcessor() {
        return asyncProcessor;
    }
}
