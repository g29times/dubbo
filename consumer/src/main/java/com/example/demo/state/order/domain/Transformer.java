package com.example.demo.state.order.domain;

@FunctionalInterface
public interface Transformer<T, D> {

    /**
     * 转换方法
     *
     * @param from T
     * @return D
     */
    D transform(T from);
}
