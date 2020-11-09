package com.example.demo.state.order.experiment.processor;

import com.example.demo.state.order.domain.Order;
import com.example.demo.state.order.state.LogisticsCreate;
import com.example.demo.state.order.state.OrderCreateStateRequest;
import com.example.demo.state.order.state.PayCreate;
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
