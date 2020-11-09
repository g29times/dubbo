package com.example.demo.state.order;

import com.example.demo.state.order.domain.Transformer;

/**
 * 业务主体上下文
 * Context即Domain 只是包装了一层
 *
 * @see org.apache.dubbo.rpc.RpcContext
 * @see org.springframework.context.ConfigurableApplicationContext
 */
public interface ContextApi<T> {

    // *************************** 流式编程区

    /**
     * 查看实体
     */
    default ContextApi<T> peek() {
        System.out.print("peek --- ");
        push(System.out::println);
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
        }.setDomain(transformer.transform(getDomain()));
    }

//    default ContextApi<T> fork(Strategy<T> strategy) {
//        strategy.process(getDomain());
//        return this;
//    }

    /**
     * 分发任务
     *
     * @return 上下文
     */
    default ContextApi<T> process() {
        getAsyncProcessor().process(getState());
        return this;
    }

    /**
     * 推进任务(默认)
     * 1 验证 - 2 处理 - 3 变更状态 - 4 通知
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
     */
//    void next(T domain);

    // *************************** 基础方法区

    Long getDomainId();

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
    RequestState<T> getState();

    /**
     * 设定状态
     *
     * @param state 状态
     * @return 上下文
     */
    ContextApi<T> setState(RequestState<T> state);

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
