package cn.huimin100.tc.owf.statemachine.order;

import cn.huimin100.tc.owf.statemachine.order.async.RequestAsyncProcessService;
import cn.huimin100.tc.owf.statemachine.order.domain.Transformer;

/**
 * 业务主体上下文
 * Context即Domain 只是包装了一层
 *
 * @see org.apache.dubbo.rpc.RpcContext
 * @see org.springframework.context.ConfigurableApplicationContext
 */
public interface RequestContext<T> {

    // *************************** 流式编程工具区

    /**
     * 主接口1 异步推进方法
     * 1 验证 - 2 处理 - 3 变更状态 - 4 通知
     */
    default RequestContext<T> process() {
        getAsyncProcessor().process(getState());
        return this;
    }

    /**
     * 主接口2 同步推进方法
     * 1 验证 - 2 处理 - 3 变更状态 - 4 通知
     */
    default RequestContext<T> next() {
        push(getState()::process);
        return this;
    }

    /**
     * 同步 处理请求
     */
    default RequestContext<T> push(Request<T> request) {
        request.process(getDomain());
        return this;
    }

    /**
     * 查看实体
     */
    default RequestContext<T> peek() {
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
    default <D> RequestContext<D> map(Transformer<T, D> transformer) {
        return new AbstractRequestContext<D>() {
        }.setDomain(transformer.transform(getDomain()));
    }

    /**
     * 获取上下文
     */
    RequestContext<T> getContext();

    // *************************** 基础方法区

    /**
     * 获取实体Id
     */
    Long getDomainId();

    /**
     * 获取实体
     */
    T getDomain();

    /**
     * 设置实体
     *
     * @param domain 实体
     * @return 上下文
     */
    RequestContext<T> setDomain(T domain);

    /**
     * 获取状态
     *
     * @return 状态
     */
    StateRequest<T> getState();

    /**
     * 设定状态
     *
     * @param state 状态
     * @return 上下文
     */
    RequestContext<T> setState(StateRequest<T> state);

    /**
     * 查询流程策略
     *
     * @return 流程策略
     */
    @Deprecated
    RequestContext<T> getStrategy();

    /**
     * 设定流程策略
     *
     * @param request 流程策略
     * @return 上下文
     */
    @Deprecated
    RequestContext<T> setStrategy(Request<T> request);

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

}
