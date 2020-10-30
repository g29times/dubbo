package com.example.demo.state.order.experiment.processor;

/**
 * https://www.cnblogs.com/javanoob/p/trade_middle-platform_design.html
 * @param <T>
 */
public interface Processor<T> {
    /**
     * 执行内容 ,中断抛出
     */
    void process(T t);

    /**
     * 触发调用下一个 Processor
     *
     */
    void fireNext(T t);

    /**
     * 检查是否执行该 Processor
     *
     * @return
     */
    boolean check(T t);
}
