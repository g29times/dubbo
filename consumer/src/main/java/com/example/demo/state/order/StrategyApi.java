package com.example.demo.state.order;

/**
 * TODO 实现类考虑单例
 *
 * @see Runnable
 * @param <T>
 */
@FunctionalInterface
public interface StrategyApi<T> {

    /**
     * 处理流程
     * @param t 实体
     */
    void process(T t);

}
