package com.example.demo.state.order.experiment.concurrent;

import com.example.demo.state.order.StrategyApi;

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
public class ProcessorPool {

    private static final long CHECK_FINISH_INTERVAL = 1000L;

    private final ExecutorService pool = Executors.newWorkStealingPool();

    public ProcessorPool() {
        RequestQueues queues = RequestQueues.getInstance();
        // 2个队列 2个线程 TODO i=?
        for (int i = 0; i < 2; i++) {
            BlockingQueue queue = new ArrayBlockingQueue<>(100);
            Processor thread = new Processor(queue);
            queues.addQueue(queue);
            pool.submit(thread);
//            futures.add(pool.submit(thread));
        }
    }

    public static void start() {
        System.out.println("POOL STARTED " + getInstance());
    }

    public void stop() {
        shutdownPool(pool, "ProcessorPool");
    }

    // TODO 待优化
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
