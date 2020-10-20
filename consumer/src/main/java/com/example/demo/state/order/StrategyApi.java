package com.example.demo.state.order;

@FunctionalInterface
public interface StrategyApi<T> {
    void request(T t);
}
