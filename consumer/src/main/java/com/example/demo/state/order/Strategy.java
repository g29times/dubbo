package com.example.demo.state.order;

/**
 * @see Runnable
 * @param <T>
 */
@FunctionalInterface
public interface Strategy<T> {

    /**
     * 处理流程
     * @param t 实体
     */
    void process(T t);
}
