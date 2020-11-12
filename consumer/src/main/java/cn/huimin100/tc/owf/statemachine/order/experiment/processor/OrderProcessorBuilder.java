package cn.huimin100.tc.owf.statemachine.order.experiment.processor;

import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.state.PayCreate;
import cn.huimin100.tc.owf.statemachine.order.state.LogisticsCreate;
import cn.huimin100.tc.owf.statemachine.order.state.OrderCreateStateRequest;
import org.springframework.stereotype.Component;

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
 * @description:
 * @date 2020/10/27 17:37
 * @see Object
 * @since 1.0
 */
@Component
public class OrderProcessorBuilder extends AbstractProcessorBuilder<Order> {

    @Override
    public OrderProcessorBuilder initProcessor() {
        addLast(new OrderCreateStateRequest());
        addLast(new PayCreate());
        addLast(new LogisticsCreate());
        return this;
    }

}
