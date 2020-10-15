package com.example.demo.state.order;

/**
 * 业务主体上下文
 */
public interface ContextApi<T> {
    /**
     * 执行任务
     */
    void request(T domain);

    /**
     * 获取状态
     */
    StateApi<T> getState();

    /**
     * 设定状态
     */
    void setState(StateApi<T> state);

    /**
     * 设定固定策略
     */
    ContextApi<T> setStrategy(StrategyApi strategy);

    /**
     * 添加策略到流式管道策略组
     */
    default ContextApi<T> addStrategy(StrategyApi strategy) {
        // TODO
        return this;
    }
}
