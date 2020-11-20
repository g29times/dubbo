package cn.huimin100.tc.owf.statemachine.order.domain;

import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;
import cn.huimin100.tc.owf.statemachine.order.state.enums.LogisticsStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.OrderStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.PayStatusEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
public class Order extends Observable implements Serializable {

    /**
     * 业务线 - 多个状态机
     */
    private int businessLine;

    /**
     * 多种状态整合
     *
     * @see OrderStatusEnum
     */
    private Map<Integer, OrderStateRequest> typeState = new HashMap<>(3);

    // *************************************** 基础属性

    private Long id;

    /**
     * @see OrderStatusEnum
     */
    private int state1;

    private int state2;

    private int state3;

    @Override
    public String toString() {
        String order = "Order{" +
                "id=" + id +
                ", typeState=" + typeState +
                '}';
        return order;
    }

    public int getBusinessLine() {
        return businessLine;
    }

    public void setBusinessLine(int businessLine) {
        this.businessLine = businessLine;
    }

    public Map<Integer, OrderStateRequest> getTypeState() {
        return typeState;
    }

    public void setTypeState(Map<Integer, OrderStateRequest> typeState) {
        this.typeState = typeState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getState1() {
        return state1;
    }

    public void setState1(int state1) {
        this.state1 = state1;
        this.typeState.put(1, OrderStatusEnum.get(state1));
//        // 观察者 - 拉模式
//        setChanged(); // 状态改变必须调用
//        notifyObservers(/*state*/);
        // 事件方式
//        ApplicationContext.publishEvent(new ApplicationEvent(this));
    }

    public int getState2() {
        return state2;
    }

    public void setState2(int state2) {
        this.state2 = state2;
        this.typeState.put(2, PayStatusEnum.get(state2));
    }

    public int getState3() {
        return state3;
    }

    public void setState3(int state3) {
        this.state3 = state3;
        this.typeState.put(3, LogisticsStatusEnum.get(state3));
    }
}
