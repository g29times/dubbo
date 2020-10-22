package com.example.demo.state.order.concurrent;

import com.example.demo.state.order.ContextApi;
import com.example.demo.state.order.StrategyApi;
import com.example.demo.state.order.context.OrderContext;
import com.example.demo.state.order.domain.Order;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>
 * <a href="www.google.com">google</a>
 *
 * @author li tong
 * @description: 请求处理线程
 * @date 2020/10/22 16:31
 * @see Object
 * @since 1.0
 */
public class Processor implements Callable<Boolean> {

    private final BlockingQueue<StrategyApi> queue;

    public Processor(BlockingQueue<StrategyApi> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        System.out.println("Thread " + Thread.currentThread().getName() + " is running");
        try {
            while (true) {
                StrategyApi<Order> request = queue.take();
                request.process(OrderContext.getOrderContext().getDomain());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

}
