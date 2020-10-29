package com.example.demo.state.order.concurrent;

import com.example.demo.state.order.StrategyApi;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

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
 * @description: 请求队列
 * @date 2020/10/22 16:31
 * @see Object
 * @since 1.0
 */
public class RequestQueues<T> {

    private RequestQueues() {
    }

    private final List<BlockingQueue<StrategyApi<T>>> queues = new CopyOnWriteArrayList<>();

    public BlockingQueue<StrategyApi<T>> getQueue(int index) {
        return queues.get(index);
    }

    public void addQueue(BlockingQueue<StrategyApi<T>> queue) {
        queues.add(queue);
    }

    public int size() {
        return queues.size();
    }

    public static  RequestQueues getInstance() {
        return RequestQueues.Singleton.INSTANCE;
    }

    private static final class Singleton {
        private static final RequestQueues INSTANCE = new RequestQueues();
    }
}
