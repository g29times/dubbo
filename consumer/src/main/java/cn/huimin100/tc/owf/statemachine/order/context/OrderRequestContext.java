package cn.huimin100.tc.owf.statemachine.order.context;

import cn.huimin100.tc.owf.statemachine.order.RequestContext;
import cn.huimin100.tc.owf.statemachine.order.Request;
import cn.huimin100.tc.owf.statemachine.order.StateRequest;
import cn.huimin100.tc.owf.statemachine.order.async.RequestAsyncProcessService;
import cn.huimin100.tc.owf.statemachine.order.async.RequestAsyncProcessServiceImpl;
import cn.huimin100.tc.owf.statemachine.order.async.observer.OrderObserver;
import cn.huimin100.tc.owf.statemachine.order.async.observer.ProcessorObserver;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;
import cn.huimin100.tc.owf.statemachine.order.state.StateMachine;
import cn.huimin100.tc.owf.statemachine.order.state.enums.StateTypeEnum;
import cn.huimin100.tc.owf.statemachine.order.state.logistics.LogisticsPick;
import cn.huimin100.tc.owf.statemachine.order.state.order.OrderCancel;
import cn.huimin100.tc.owf.statemachine.order.state.order.OrderCreate;
import cn.huimin100.tc.owf.statemachine.order.state.order.OrderFinish;
import cn.huimin100.tc.owf.statemachine.order.state.pay.PayWaiting;
import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.HashMap;
import java.util.Map;

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
public class OrderRequestContext implements RequestContext<Order> {

	private static final TransmittableThreadLocal<OrderRequestContext> THREAD_CONTEXT =
			new TransmittableThreadLocal<OrderRequestContext>() {
				@Override
				protected OrderRequestContext initialValue() {
					return new OrderRequestContext();
				}
			};

	private OrderRequestContext() {
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

	public OrderRequestContext(Order domain) {
		// 初始化上下文
		System.out.println(Thread.currentThread() + " --- OrderContext <" + this.hashCode() + "> is created! --- ");
		// 创建状态集
		this.orderCreate = new OrderCreate();
//		this.orderFinish = new OrderFinish();
//		this.orderCancel = new OrderCancel();
//		this.payCreate = new PayWaiting();
//		this.logisticsCreate = new LogisticsPick();
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

	public static OrderRequestContext getThreadContext() {
		OrderRequestContext orderDomain = THREAD_CONTEXT.get();
		return orderDomain;
	}

	public static OrderRequestContext getThreadContext(Order domain) {
//        RequestAsyncProcessService asyncProcessor = RequestAsyncProcessServiceImpl.getInstance();
//        Processor processor = asyncProcessor.getProcessor(domain.getId());
//        OrderContext orderDomain = processor.getOrderContext();
//        orderDomain.setDomain(domain);
		OrderRequestContext context = THREAD_CONTEXT.get();
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
	private OrderStateRequest orderCreate;
	private OrderStateRequest payCreate;
	private OrderStateRequest logisticsCreate;
	private OrderStateRequest orderFinish;
	private OrderStateRequest orderCancel;

	/**
	 * 策略 持有一个Strategy类型的对象实例
	 */
	private Request<Order> request;

	// *************************** 基础方法区

	@Override
	public RequestContext<Order> getContext() {
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
	public RequestContext<Order> setDomain(Order domain) {
		setState(domain);
		this.domain = domain;
		return this;
	}

	private void setState(Order domain) {
//		StateMachine machine = StateMachine.getInstanceByHash(domain);
		// 刚创建 设置初始状态
		if (/*domain.getState() == null || domain.getState() == 0 || */
				domain.getTypeState() == null || domain.getTypeState().get(1) == null || domain.getTypeState().get(1).getStateValue() == 0) {
			// 默认为订单状态
			this.state = this.orderCreate;
			this.state.setContext(this);

//			Map<Integer, StateRequest<Order>> map = new HashMap<>(1);
//			map.put(1, this.orderCreate);
//			map.put(2, this.payCreate);
//			map.put(3, this.logisticsCreate);
//			domain.setTypeState(map);
			domain.setState1(this.orderCreate.getStateValue());
			System.out.println("<" + this + "> - " + "订单已创建 " + domain);
		} else {
			this.state = StateTypeEnum.get(domain.getTypeState());
			this.state.setContext(this);
		}
	}

	@Override
	public StateRequest<Order> getState() {
		return state;
	}

	@Override
	public RequestContext<Order> setState(StateRequest<Order> state) {
		this.state = (OrderStateRequest) state;
		return this;
	}

	@Override
	public RequestContext<Order> getStrategy() {
		return this;
	}

	@Override
	public RequestContext<Order> setStrategy(Request<Order> request) {
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
