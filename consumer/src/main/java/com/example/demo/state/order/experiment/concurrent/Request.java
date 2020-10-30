package com.example.demo.state.order.experiment.concurrent;

import com.example.demo.state.order.StrategyApi;
import com.example.demo.state.order.domain.Order;

import java.util.function.Supplier;

public interface Request<T> extends StrategyApi<T> {

    void process();

    void process(Supplier supplier);

    Long getDomainId();

}
