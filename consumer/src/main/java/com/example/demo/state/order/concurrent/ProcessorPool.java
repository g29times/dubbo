package com.example.demo.state.order.concurrent;

import com.example.demo.state.order.ContextApi;
import com.example.demo.state.order.StrategyApi;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

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
 * @description: 请求处理线程池
 * @date 2020/10/22 15:58
 * @see Object
 * @since 1.0
 */
public class ProcessorPool<T> {

    private static final long CHECK_FINISH_INTERVAL = 1000L;

    private final ExecutorService pool = Executors.newWorkStealingPool();

    private ContextApi<T> context;

    public ProcessorPool() {
        RequestQueues<T> queues = RequestQueues.getInstance();
        // 2个队列 2个线程 TODO i=2 i=?
        for (int i = 0; i < 2; i++) {
            BlockingQueue<StrategyApi<T>> queue = new ArrayBlockingQueue<>(100);
            Processor<T> thread = new Processor(queue, context);
            queues.addQueue(queue);
            pool.submit(thread);
//            futures.add(pool.submit(thread));
        }
    }

    private static Set<Future<Boolean>> futures;

    public static Boolean get() {
        try {
//            futures.forEach(future -> future.get());
//            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void start(/*ContextApi context*/) {
//        this.context = context;
        System.out.println("POOL STARTED " + getInstance());
    }

    public void stop() {
        shutdownPool(pool, "ProcessorPool");
    }

    public static void shutdownPool(ExecutorService pool, String poolName) {
        pool.shutdown();
        LongAdder innerTimeAdder = new LongAdder();
        try {
            while (!pool.awaitTermination(CHECK_FINISH_INTERVAL, TimeUnit.MILLISECONDS)) {
                System.out.println(poolName + "{} did not terminate, wait " + innerTimeAdder.longValue() + "s for shutdown. {}" + pool);
                innerTimeAdder.add(CHECK_FINISH_INTERVAL);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            pool.shutdownNow();
        }
        // 所有任务执行完毕
        System.out.println(poolName + " Pool Closed : " + pool);
//        logger.debug("{} Pool Closed : {} ", poolName, pool);
    }

    public static ProcessorPool getInstance() {
        return Singleton.INSTANCE;
    }

    private static final class Singleton {
        private static final ProcessorPool INSTANCE = new ProcessorPool();
    }

}
