package cn.huimin100.tc.owf.statemachine.order;

/**
 * @see Runnable
 * @param <T>
 */
@FunctionalInterface
public interface Request<T> {

    /**
     * 处理流程
     * @param t 实体
     */
    void process(T t);
}
