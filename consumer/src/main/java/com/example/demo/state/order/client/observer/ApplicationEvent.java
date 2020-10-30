package com.example.demo.state.order.client.observer;

import java.util.EventObject;

/**
 * 事件类
 * <p>
 * 事件驱动模型三大要素
 * 1）事件源：能接收外部事件的源体；
 * 2）监听器Listener：能接收事件源通知的对象；
 * 3）处理器Handler：用于处理事件的对象。
 * <p>
 * https://www.cnblogs.com/adamjwh/p/10913660.html
 * https://www.jianshu.com/p/724e5814f78b
 * https://www.cnblogs.com/EasonJim/p/7101203.html
 * https://blog.csdn.net/xuexiangjys/article/details/53055223
 * https://blog.csdn.net/maple_son/article/details/87629742
 *
 * @author li tong
 * @date 2019/7/26 10:59
 * @see org.springframework.context.ApplicationEvent
 * @since 1.0
 */
public class ApplicationEvent extends EventObject {

    private static final long serialVersionUID = -2394943358459605968L;

    private final long timestamp = System.currentTimeMillis();

    public final long getTimestamp() {
        return this.timestamp;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }

}
