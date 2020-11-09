package com.example.demo.state.order;


import com.example.demo.state.order.experiment.concurrent.Processor;

import java.util.concurrent.BlockingQueue;

/**
 * 请求异步执行的service
 *
 * @author Administrator
 */
public interface RequestAsyncProcessService {

    void process(RequestState stateRequest);

    void put(BlockingQueue key, Processor value);

    Processor get(BlockingQueue key);

    Processor getProcessor(Long productId);
}
