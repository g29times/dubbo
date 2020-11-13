package cn.huimin100.tc.owf.statemachine.order.experiment.strategy;

import cn.huimin100.tc.owf.statemachine.order.ContextApi;
import cn.huimin100.tc.owf.statemachine.order.Request;
import cn.huimin100.tc.owf.statemachine.order.context.OrderContext;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.state.enums.OrderStatusEnum;
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
 * @description: 退货流程
 * @date 2020/10/14 20:12
 * @see AbstractProcessorBuilder
 * @since 1.0
 */
public class OrderCancelRequest implements Request<Order> {

    private OrderContext context;

    public OrderCancelRequest(ContextApi<Order> context) {
        this.context = (OrderContext) context;
    }

    @Override
    public void process(Order order) {
        OrderStateRequest state = OrderStatusEnum.CANCEL.getState();
        state.reverse(order);
        state.next(order);
        state.log(order);
    }
}
