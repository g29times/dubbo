package com.example.demo.state.order.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.example.demo.state.order.*;
import com.example.demo.state.order.client.observer.OrderObserver;
import com.example.demo.state.order.client.observer.ProcessorObserver;
import com.example.demo.state.order.domain.Order;
import com.example.demo.state.order.state.*;

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
		this.orderCreate = new OrderCreateStateRequest();
		this.orderFinish = new OrderFinishStateRequest();
		this.orderCancel = new OrderCancelStateRequest();
		this.payCreate = new PayCreate();
		this.logisticsCreate = new LogisticsCreate();
		// 开始监听
		OrderObserver.listen();
		ProcessorObserver.listen();
	}

	public OrderContext(Order domain) {
		// 初始化上下文
		System.out.println(Thread.currentThread() + " --- OrderContext <" + this.hashCode() + "> is created! --- ");
		// 创建状态集
		this.orderCreate = new OrderCreateStateRequest();
		this.orderFinish = new OrderFinishStateRequest();
		this.orderCancel = new OrderCancelStateRequest();
		this.payCreate = new PayCreate();
		this.logisticsCreate = new LogisticsCreate();
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
        OrderContext context = THREAD_CONTEXT.get();
        context.setDomain(domain);
        return context;
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
	private OrderStateRequest state;

	/**
	 * 状态 定义出所有状态
	 */
	private final OrderStateRequest orderCreate;
	private final OrderStateRequest orderFinish;
	private final OrderStateRequest orderCancel;
	private final OrderStateRequest payCreate;
	private final OrderStateRequest logisticsCreate;

	/**
	 * 策略 持有一个Strategy类型的对象实例
	 */
	private Strategy strategy;

	// *************************** 基础方法区

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
		if (domain.getState() == null || domain.getState() == 0) {
			// 设置初始状态
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
		strategy.process(order);
	}

	@Override
	public RequestAsyncProcessService getAsyncProcessor() {
		return RequestAsyncProcessServiceImpl.getInstance();
	}
}
