package com.example.demo.state.order.domain;

import com.example.demo.state.order.client.observer.ApplicationContext;
import com.example.demo.state.order.client.observer.ApplicationEvent;
import com.example.demo.state.order.state.OrderStatusEnum;

import java.util.Observable;

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
 * @description: 订单实体
 * @date 2020/10/14 18:13
 * @see 观察者模式 https://www.cnblogs.com/java-my-life/archive/2012/05/16/2502279.html
 * @since 1.0
 */
public class Order extends Observable {

    private Long id;

    /**
     * @see com.example.demo.state.order.state.OrderStatusEnum
     */
    private int state;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", state=" + state +
                ", desc=" + OrderStatusEnum.get(state).getDesc() +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
//        // 观察者 - 拉模式
//        setChanged(); // 状态改变必须调用
//        notifyObservers(/*state*/);
        // 事件方式
//        ApplicationContext.publishEvent(new ApplicationEvent(this));
    }
}
