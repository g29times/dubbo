package com.example.demo.state.order.client.observer;

import com.example.demo.state.order.experiment.concurrent.Processor;

import java.util.concurrent.ExecutionException;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>
 * <a href="https://www.cnblogs.com/java-my-life/archive/2012/05/16/2502279.html">google</a>
 * https://blog.csdn.net/weixin_39035120/article/details/86225377
 *
 * @author li tong
 * @description: 观察者实例
 * @date 2020/09/10 13:27
 * @see Object
 * @since 1.0
 */
public class ProcessorObserver {
    /**
     * event方式
     */
    public static void listen() {
        ApplicationContext.addListener(event -> {
            if (event.getSource() instanceof Processor) {
                try {
                    System.out.println(System.currentTimeMillis() + " 监听到线程已处理完成任务：" + Thread.currentThread());
                    // TODO 不可用 原因待查
//                    ((Processor) event.getSource()).getResult().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
