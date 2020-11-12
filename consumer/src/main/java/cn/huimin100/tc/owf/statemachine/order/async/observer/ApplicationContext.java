package cn.huimin100.tc.owf.statemachine.order.async.observer;

import java.util.HashSet;
import java.util.Set;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>
 * 运行上下文环境
 *
 * @author li tong
 * @date 2019/7/26 11:00
 * @see Object
 * @since 1.0
 */
public class ApplicationContext {

    /**
     * 存放所有的监听器
     */
    private static final Set<ApplicationListener<ApplicationEvent>> listeners = new HashSet<>();

    private ApplicationContext() {
    }

    /**
     * 添加监听器
     *
     * @param listener 监听器
     */
    public static void addListener(ApplicationListener<ApplicationEvent> listener) {
        listeners.add(listener);
    }

    /**
     * 发布事件
     * 回调所有监听器的回调方法
     *
     * @param event 事件
     */
    public static void publishEvent(ApplicationEvent event) {
        for (ApplicationListener<ApplicationEvent> listener : listeners) {
            listener.handleEvent(event);
        }
    }

}
