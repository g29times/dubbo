package com.example.demo.state.order;

@FunctionalInterface
public interface StrategyApi<T> {

    void requestStrategy(T t);

}
