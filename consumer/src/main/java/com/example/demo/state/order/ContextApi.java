package com.example.demo.state.order;

/**
 * 业务主体上下文
 * @see org.apache.dubbo.rpc.RpcContext
 */
public interface ContextApi<T> {

    default ContextApi<T> of(T domain) {
        setDomain(domain);
        return this;
    }

    default ContextApi<T> fork(StrategyApi<T> strategyApi) {
        strategyApi.process(getDomain());
        return this;
    }

    default T get() {
        return getDomain();
    }

    @Deprecated
    void request(T domain);

    /**
     * 获取实体
     */
    T getDomain();

    /**
     * 设置实体
     */
    ContextApi<T> setDomain(T domain);

    /**
     * 获取状态
     */
    StateApi<T> getState();

    /**
     * 设定状态
     */
    ContextApi<T> setState(StateApi<T> state);

    /**
     * 查询策略
     */
    StrategyApi<T> getStrategy();

    /**
     * 设定固定策略
     */
    ContextApi<T> setStrategy(StrategyApi<T> strategy);

}
