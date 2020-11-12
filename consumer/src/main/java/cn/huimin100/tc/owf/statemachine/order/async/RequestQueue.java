package cn.huimin100.tc.owf.statemachine.order.async;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

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
public class RequestQueue {

    /**
     * 内存队列
     */
    private final List<BlockingQueue> queues = new ArrayList<>();
    /**
     * 标识位map
     */
    private final Map<Long, Boolean> flagMap = new ConcurrentHashMap<>();

    private RequestQueue() {
    }

    public static RequestQueue getInstance() {
        return RequestQueue.Singleton.INSTANCE;
    }

    public BlockingQueue getQueue(int index) {
        return queues.get(index);
    }

    public void addQueue(BlockingQueue queue) {
        queues.add(queue);
    }

    public int queueSize() {
        return queues.size();
    }

    public Map<Long, Boolean> getFlagMap() {
        return flagMap;
    }

    private static final class Singleton {
        private static final RequestQueue INSTANCE = new RequestQueue();
    }
}
