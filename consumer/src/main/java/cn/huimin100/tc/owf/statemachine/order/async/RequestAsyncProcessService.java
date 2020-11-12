package cn.huimin100.tc.owf.statemachine.order.async;


import cn.huimin100.tc.owf.statemachine.order.StateRequest;

import java.util.concurrent.BlockingQueue;

/**
 * 请求异步执行的service
 *
 * @author Administrator
 */
public interface RequestAsyncProcessService {

    void process(StateRequest stateRequest);

    void put(BlockingQueue key, Processor value);

    Processor get(BlockingQueue key);

    Processor getProcessor(Long productId);
}
