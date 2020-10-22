package com.example.demo.state.order.concurrent;

import com.example.demo.state.order.StrategyApi;
import com.example.demo.state.order.domain.Order;

public interface Request extends StrategyApi/*<Order>*/ {

    void process();

    Long getOrderId();

}
