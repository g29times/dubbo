package com.example.demo.state.order.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.example.demo.state.order.*;
import com.example.demo.state.order.client.observer.OrderObserver;
import com.example.demo.state.order.client.observer.ProcessorObserver;
import com.example.demo.state.order.domain.Order;
import com.example.demo.state.order.experiment.concurrent.Processor;
import com.example.demo.state.order.state.OrderRequestState;
import com.example.demo.state.order.state.OrderStatusEnum;

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

    private static final TransmittableThreadLocal<OrderContext> THREAD_CONTEXT =
            new TransmittableThreadLocal<OrderContext>() {
                @Override
                protected OrderContext initialValue() {
                    return new OrderContext();
                }
            };

    private OrderContext() {
        // 初始化上下文
        System.out.println(Thread.currentThread() + " --- OrderContext <" + this.hashCode() + "> is created! --- ");
        // 创建状态集
        this.orderCreate = OrderStatusEnum.CREATE.getState();
        this.orderFinish = OrderStatusEnum.FINISH.getState();
        this.orderCancel = OrderStatusEnum.CANCLE.getState();
        this.payCreate = OrderStatusEnum.PAY.getState();
        this.logisticsCreate = OrderStatusEnum.LOGISTICS.getState();
        // 开始监听
        OrderObserver.listen();
        ProcessorObserver.listen();
    }

    @Override
    protected void finalize() throws Throwable {
        removeContext();
        super.finalize();
        System.out.println("Context is disposed!");
    }

    public static OrderContext getThreadContext() {
        OrderContext orderDomain = THREAD_CONTEXT.get();
        return orderDomain;
    }

    public static OrderContext getThreadContext(Order domain) {
//        RequestAsyncProcessService asyncProcessor = RequestAsyncProcessServiceImpl.getInstance();
//        Processor processor = asyncProcessor.getProcessor(domain.getId());
//        OrderContext orderDomain = processor.getOrderContext();
//        orderDomain.setDomain(domain);
        return THREAD_CONTEXT.get();
    }

    private static void removeContext() {
        System.out.println("removeContext");
        THREAD_CONTEXT.remove();
    }

    // ************************************** 基础属性区

    private Order domain;

    /**
     * 状态 持有一个State类型的对象实例
     */
    private OrderRequestState state;

    /**
     * 状态 定义出所有状态
     */
    private final OrderRequestState orderCreate;
    private final OrderRequestState orderFinish;
    private final OrderRequestState orderCancel;
    private final OrderRequestState payCreate;
    private final OrderRequestState logisticsCreate;

    /**
     * 策略 持有一个Strategy类型的对象实例
     */
    private Strategy strategy;

    // *************************** 基础方法区

    @Override
    public Long getDomainId() {
        return getDomain().getId();
    }

    @Override
    public Order getDomain() {
        return domain;
    }

    @Override
    public ContextApi<Order> setDomain(Order domain) {
//        if (domain.getState() == null || domain.getState() == 0) {
//            LOCAL_STATE.set(OrderStatusEnum.CREATE.getState());
//            domain.setState(OrderStatusEnum.CREATE.getState().getStateValue());
//            System.out.println("<" + this + "> - " + "订单已创建 " + domain);
//        } else {
//            LOCAL_STATE.set(OrderStatusEnum.get(domain.getState()));
//        }
//        LOCAL_DOMAIN.set(domain);

        if (domain.getState() == null || domain.getState() == 0) {
            // 设置初始状态
            this.state = this.orderCreate;
            domain.setState(this.orderCreate.getStateValue());
//        db.insert(domain);
            System.out.println("<" + this + "> - " + "订单已创建 " + domain);
        } else {
            this.state = OrderStatusEnum.get(domain.getState());
        }
        this.domain = domain;
        return this;
    }

    @Override
    public RequestState getState() {
        return state;
    }

    @Override
    public ContextApi setState(RequestState state) {
        this.state = (OrderRequestState) state;
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


    public OrderRequestState getOrderCreate() {
        return orderCreate;
    }

    public OrderRequestState getOrderFinish() {
        return orderFinish;
    }

    public OrderRequestState getOrderCancel() {
        return orderCancel;
    }

    public OrderRequestState getPayCreate() {
        return payCreate;
    }

    public OrderRequestState getLogisticsCreate() {
        return logisticsCreate;
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

    @Override
    public RequestAsyncProcessService getAsyncProcessor() {
        return RequestAsyncProcessServiceImpl.getInstance();
    }
}
