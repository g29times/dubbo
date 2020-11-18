package cn.huimin100.tc.owf.statemachine.order.experiment.strategy;

import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.RequestContext;
import cn.huimin100.tc.owf.statemachine.order.context.OrderRequestContext;
import cn.huimin100.tc.owf.statemachine.order.Request;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;

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
public class OrderCreateRequest implements Request<Order> {

	private OrderRequestContext context;

	public OrderCreateRequest(RequestContext<Order> context) {
		this.context = (OrderRequestContext) context;
	}

	@Override
	public void process(Order order) {
		OrderStateRequest state = context.getOrderCreate();
		state.pre(order);
		state.change(order);
		state.log(order);
	}

}
