package cn.huimin100.tc.owf.statemachine.order.domain;

import cn.huimin100.tc.owf.statemachine.order.StateRequest;
import cn.huimin100.tc.owf.statemachine.order.state.enums.OrderStatusEnum;

import java.io.Serializable;
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

	private Long id;

	/**
	 * 类型状态 <StateType, StateRequest>
	 * @see OrderStatusEnum
	 */
	private Map<Integer, StateRequest<Order>> typeState;

	/**
	 * @see OrderStatusEnum
	 */
	@Deprecated
	private int state;

	@Override
	public String toString() {
		String order = "Order{" +
				"id=" + id +
				", typeState=" + typeState +
				'}';
		return order;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<Integer, StateRequest<Order>> getTypeState() {
		return typeState;
	}

	public void setTypeState(Map<Integer, StateRequest<Order>> typeState) {
		this.typeState = typeState;
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
