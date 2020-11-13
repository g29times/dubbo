package cn.huimin100.tc.owf.statemachine.order.context;

import cn.huimin100.tc.owf.statemachine.order.ContextApi;
import cn.huimin100.tc.owf.statemachine.order.Request;
import cn.huimin100.tc.owf.statemachine.order.StateRequest;
import cn.huimin100.tc.owf.statemachine.order.async.RequestAsyncProcessService;
import cn.huimin100.tc.owf.statemachine.order.async.RequestAsyncProcessServiceImpl;
import cn.huimin100.tc.owf.statemachine.order.async.observer.OrderObserver;
import cn.huimin100.tc.owf.statemachine.order.async.observer.ProcessorObserver;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.state.enums.OrderStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.logistics.LogisticsPick;
import cn.huimin100.tc.owf.statemachine.order.state.order.OrderCancel;
import cn.huimin100.tc.owf.statemachine.order.state.order.OrderCreate;
import cn.huimin100.tc.owf.statemachine.order.state.order.OrderFinish;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;
import cn.huimin100.tc.owf.statemachine.order.state.pay.PayWaiting;
import com.alibaba.ttl.TransmittableThreadLocal;

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
        this.orderCreate = new OrderCreate();
        this.orderFinish = new OrderFinish();
        this.orderCancel = new OrderCancel();
        this.payCreate = new PayWaiting();
        this.logisticsCreate = new LogisticsPick();
        // 开始监听
        OrderObserver.listen();
        ProcessorObserver.listen();
    }

    public OrderContext(Order domain) {
        // 初始化上下文
        System.out.println(Thread.currentThread() + " --- OrderContext <" + this.hashCode() + "> is created! --- ");
        // 创建状态集
        this.orderCreate = new OrderCreate();
        this.orderFinish = new OrderFinish();
        this.orderCancel = new OrderCancel();
        this.payCreate = new PayWaiting();
        this.logisticsCreate = new LogisticsPick();
        // 开始监听
        OrderObserver.listen();
        ProcessorObserver.listen();
        // 设置域
        setDomain(domain);
    }

    @Override
    protected void finalize() throws Throwable {
        removeContext();
        super.finalize();
        System.out.println("Context " + this.hashCode() + "is disposed!");
    }

    private static void removeContext() {
        System.out.print("ThreadLocal removed, ");
        THREAD_CONTEXT.remove();
    }

	@Override
	public String toString() {
		return this.hashCode() + "";
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
        OrderContext context = THREAD_CONTEXT.get();
        context.setDomain(domain);
        return context;
    }

    // ************************************** 基础属性区

	/**
	 * 包含领域
	 */
    private Order domain;

    /**
     * 状态模式 持有一个State类型的对象实例
     */
    private OrderStateRequest state;

    /**
     * 状态模式 定义出所有状态
     */
    private final OrderStateRequest orderCreate;
    private final OrderStateRequest orderFinish;
    private final OrderStateRequest orderCancel;
    private final OrderStateRequest payCreate;
    private final OrderStateRequest logisticsCreate;

    /**
     * 策略 持有一个Strategy类型的对象实例
     */
    private Request request;

    // *************************** 基础方法区

	@Override
	public ContextApi<Order> getContext() {
		return this;
	}

	@Override
    public Order getDomain() {
        return domain;
    }

    @Override
    public Long getDomainId() {
        return getDomain().getId();
    }

    @Override
    public ContextApi<Order> setDomain(Order domain) {
        setState(domain);
        this.domain = domain;
        return this;
    }

    private void setState(Order domain) {
	    // 默认为订单状态
        domain.setStateType(1);
        // 刚创建 设置初始状态
        if (domain.getState() == null || domain.getState() == 0) {
            this.state = this.orderCreate;
            this.state.setContext(this);
            domain.setState(this.orderCreate.getStateValue());
            System.out.println("<" + this + "> - " + "订单已创建 " + domain);
        } else {
            this.state = OrderStatusEnum.get(domain.getState());
            this.state.setContext(this);
        }
    }

    @Override
    public StateRequest getState() {
        return state;
    }

    @Override
    public ContextApi setState(StateRequest state) {
        this.state = (OrderStateRequest) state;
        return this;
    }

    @Override
    public Request getStrategy() {
        return request;
    }

    @Override
    public ContextApi setStrategy(Request request) {
        this.request = request;
        return this;
    }



    public OrderStateRequest getOrderCreate() {
        return orderCreate;
    }

    public OrderStateRequest getOrderFinish() {
        return orderFinish;
    }

    public OrderStateRequest getOrderCancel() {
        return orderCancel;
    }

    public OrderStateRequest getPayCreate() {
        return payCreate;
    }

    public OrderStateRequest getLogisticsCreate() {
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
        request.process(order);
    }

    @Override
    public RequestAsyncProcessService getAsyncProcessor() {
        return RequestAsyncProcessServiceImpl.getInstance();
    }
}
