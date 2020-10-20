package com.example.demo.state.order;

// TODO 实现类考虑单例
@FunctionalInterface
public interface StrategyApi<T> {

    void process(T t);

}
