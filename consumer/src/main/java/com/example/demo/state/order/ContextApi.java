package com.example.demo.state.order;

import com.example.demo.state.order.concurrent.RequestQueues;
import com.example.demo.state.order.domain.Transformer;

import java.util.concurrent.BlockingQueue;

/**
 * 业务主体上下文
 *
 * @see org.apache.dubbo.rpc.RpcContext
 */
public interface ContextApi<T> {

    // *************************** 流式编程区

    /**
     * 包含实体
     *
     * @param domain 实体
     * @return 上下文
     */
    default ContextApi<T> of(T domain) {
        return setDomain(domain);
    }

    /**
     * 分发任务
     *
     * @param strategyApi 流程策略
     * @return 上下文
     */
    default ContextApi<T> fork(StrategyApi<T> strategyApi) {
        RequestQueues requestQueues = RequestQueues.getInstance();
        BlockingQueue<StrategyApi> queue = requestQueues.getQueue(0);
        try {
            queue.put(strategyApi);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 推进任务
     *
     * @param strategyApi 流程策略
     * @return 上下文
     */
    default ContextApi<T> push(StrategyApi<T> strategyApi) {
        strategyApi.process(getDomain());
        return this;
    }

    /**
     * 获取实体
     *
     * @return 上下文
     */
    default T get() {
        return getDomain();
    }

    /**
     * 转换实体
     *
     * @param transformer 转换器
     * @param <D>         目标类型
     * @return 目标类型的上下文
     */
    default <D> ContextApi<D> map(Transformer<T, D> transformer) {
        return new AbstractContext<D>() {
        }.of(transformer.transform(getDomain()));
    }

    // *************************** 业务方法区

    /**
     * 执行流程
     *
     * @param domain 实体
     */
    @Deprecated
    void request(T domain);

    /**
     * 下推流程
     *
     * @param domain 实体
     */
//    void next(T domain);

    // *************************** 基础方法区

    /**
     * 获取实体
     *
     * @return 实体
     */
    T getDomain();

    /**
     * 设置实体
     *
     * @param domain 实体
     * @return 上下文
     */
    ContextApi<T> setDomain(T domain);

    /**
     * 获取状态
     *
     * @return 状态
     */
    StateApi<T> getState();

    /**
     * 设定状态
     *
     * @param state 状态
     * @return 上下文
     */
    ContextApi<T> setState(StateApi<T> state);

    /**
     * 查询流程策略
     *
     * @return 流程策略
     */
    StrategyApi<T> getStrategy();

    /**
     * 设定流程策略
     *
     * @param strategy 流程策略
     * @return 上下文
     */
    ContextApi<T> setStrategy(StrategyApi<T> strategy);

}
