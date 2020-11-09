package com.example.demo.state.order.experiment.strategy;

import com.example.demo.state.order.ContextApi;
import com.example.demo.state.order.context.OrderContext;
import com.example.demo.state.order.domain.Order;
import com.example.demo.state.order.Strategy;
import com.example.demo.state.order.state.OrderRequestState;

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
 * @description: 创建流程
 * @date 2020/10/14 20:12
 * @see AbstractProcessorBuilder
 * @since 1.0
 */
public class OrderCreateStrategy implements Strategy<Order> {

	private OrderContext context;

	public OrderCreateStrategy(ContextApi<Order> context) {
		this.context = (OrderContext) context;
	}

	@Override
	public void process(Order order) {
		OrderRequestState state = context.getOrderCreate();
		state.update(order);
		state.next(order);
		state.log(order);
	}

}
