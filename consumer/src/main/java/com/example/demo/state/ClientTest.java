package com.example.demo.state;

import com.example.demo.state.demo.ConcreteStateA;
import com.example.demo.state.demo.ConcreteStateB;
import com.example.demo.state.demo.Context;
import com.example.demo.state.demo.State;
import com.example.demo.state.order.*;
import com.example.demo.state.order.state.OrderCreateState;
import com.example.demo.state.order.state.OrderReverseState;
import com.example.demo.state.order.state.OrderState;
import com.example.demo.state.order.strategy.OrderCreateStrategy;
import com.example.demo.state.order.strategy.OrderReverseStrategy;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>
 * <a href="https://www.cnblogs.com/xyzq/p/11090344.html">状态模式</a>
 *
 * @author li tong
 * @description:
 * @date 2020/10/14 16:57
 * @see Object
 * @since 1.0
 */
public class ClientTest {

	public static void main(String[] args) {
//        demo();
//        orderV1();
//		orderV2();
		orderV3();
	}

	static class AnyStrategy {
		public static void process(Order order) {
			// TODO 需配置化
			OWFContext context = OWFContext.getContext();
			OrderState create = context.getCreateState();
			create.update(order);
//        db.insert(order);
			create.inform(order);
			create.log(order);
			System.out.println();
		}
	}

	private static void orderV3() {
		ContextApi<Order> orderFlow = new OWFContext();
		Order order = new Order();
		order.setId(1234L);
        // order.create() | orderService.create(order);
        order = orderFlow.of(order).fork(AnyStrategy::process).fork(AnyStrategy::process).get();
        System.out.println(order);

		orderFlow = null; // GC
		System.gc();
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void orderV2() {
		ContextApi<Order> orderFlow = new OWFContext();
		Order order = new Order();
		order.setId(1234L);
		orderFlow.setStrategy(new OrderCreateStrategy(orderFlow)).request(order);
		orderFlow.setStrategy(new OrderReverseStrategy(orderFlow)).request(order);
	}

	private static void orderV1() {
		ContextApi<Order> orderFlow = new OWFContext();
		Order order = new Order();

		StateApi<Order> create = new OrderCreateState(orderFlow);
		orderFlow.setState(create);
		StrategyApi<Order> createStrategy = new OrderCreateStrategy(orderFlow);
		orderFlow.setStrategy(createStrategy);
		orderFlow.request(order);
		System.out.println();

		StateApi<Order> reverse = new OrderReverseState(orderFlow);
		orderFlow.setState(reverse);
		StrategyApi<Order> reverseStrategy = new OrderReverseStrategy(orderFlow);
		orderFlow.setStrategy(reverseStrategy);
		orderFlow.request(order);
	}

	private static void demo() {
		//创建环境
		Context context = new Context();
		//创建状态
		State stateA = new ConcreteStateA();
		//将状态设置到环境中
		context.setState(stateA);
		//请求
		context.request("common flow");
		//创建状态
		State stateB = new ConcreteStateB();
		//将状态设置到环境中
		context.setState(stateB);
		//请求
		context.request("common flow");
	}
}
