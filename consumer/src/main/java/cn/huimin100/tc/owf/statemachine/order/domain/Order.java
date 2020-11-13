package cn.huimin100.tc.owf.statemachine.order.domain;

import cn.huimin100.tc.owf.statemachine.order.state.enums.LogisticsStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.OrderStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.PayStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.StateTypeEnum;

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
     * @see OrderStatusEnum
     */
    private int state;

    /**
     * 状态类型
     */
    private int stateType;

    @Override
    public String toString() {
        String order = "";
        try {
            order = "Order{" +
                    "id=" + id +
                    ", state=" + state +
                    ", type=" + StateTypeEnum.get(stateType) +
                    ", desc=" + (
                    stateType == 1 ? OrderStatusEnum.get(state).getStateDesc() :
                            stateType == 2 ? PayStatusEnum.get(state).getStateDesc() :
                                    LogisticsStatusEnum.get(state).getStateDesc()
            ) +
                    '}';
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
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

    public int getStateType() {
        return stateType;
    }

    public void setStateType(int stateType) {
        this.stateType = stateType;
    }
}
