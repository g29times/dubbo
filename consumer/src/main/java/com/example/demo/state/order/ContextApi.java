package com.example.demo.state.order;

import com.example.demo.state.order.domain.Transformer;
import com.example.demo.state.order.experiment.concurrent.RequestQueue;

import java.util.concurrent.BlockingQueue;

/**
 * 业务主体上下文
 *
 * @see org.apache.dubbo.rpc.RpcContext
 * @see org.springframework.context.ConfigurableApplicationContext
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
     * 查看实体
     */
    default ContextApi<T> peek() {
        System.out.print("peek --- ");
        fork(System.out::println);
        return this;
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

    /**
     * 分发任务
     *
     * @param strategy 流程策略
     * @return 上下文
     */
    default ContextApi<T> fork(Strategy<T> strategy) {
        strategy.process(getDomain());
        return this;
    }

    default ContextApi<T> process() {
        getAsyncProcessor().process(getState());
        return this;
    }

    /**
     * 验证 - 处理] - 变更状态 - [通知后驱
     */
    default ContextApi<T> next() {
//        fork(getState()::next);
        push(getState()::next);
        return this;
    }

    /**
     * 推进任务
     *
     * @param strategy 流程策略
     * @return 上下文
     */
    default ContextApi<T> push(Strategy<T> strategy) {
        strategy.process(getDomain());
        return this;
    }

    // *************************** 业务方法区

    /**
     * 获取异步处理器
     */
    RequestAsyncProcessService getAsyncProcessor();

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
    Long getDomainId();

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
    Strategy<T> getStrategy();

    /**
     * 设定流程策略
     *
     * @param strategy 流程策略
     * @return 上下文
     */
    ContextApi<T> setStrategy(Strategy<T> strategy);

}
