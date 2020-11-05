package com.example.demo.state.order.experiment.concurrent;

import com.example.demo.state.order.StateApi;
import com.example.demo.state.order.context.OrderContext;

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

    private final BlockingQueue<StateApi> queue;

    public Processor(BlockingQueue<StateApi> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        System.out.println("Thread " + Thread.currentThread().getName() + " is running");
        try {
            while (true) {
                StateApi request = queue.take();
                request.process(OrderContext.getInstance().getDomain());
            }
        } catch (Exception e) {
            // 线程内异常捕获 Future https://blog.csdn.net/zangdaiyang1991/article/details/89228103
            e.printStackTrace();
        }
        return true;
    }

}
