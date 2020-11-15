package cn.huimin100.tc.owf.statemachine.order.state.enums;

import cn.huimin100.tc.owf.statemachine.order.StateRequest;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态类型码
 *
 * @author litong
 * @program:
 * @description:
 * @create: 2020-11-13
 */
public enum StateTypeEnum {

	/**
	 * 订单
	 */
	ORDER(1, "Order"),

	/**
	 * 支付
	 */
	PAY(2, "Pay"),

	/**
	 * 物流
	 */
	LOGISTICS(3, "Logistics");

	StateTypeEnum(int code, String state) {
		this.code = code;
		this.state = state;
	}

	private final int code;

	private final String state;

	private static Map<Integer, StateTypeEnum> enumMaps = new HashMap<>();

	static {
		for (StateTypeEnum e : StateTypeEnum.values()) {
			enumMaps.put(e.getCode(), e);
		}
	}

	/**
	 * 根据id查询名称
	 *
	 * @param id id
	 * @return 名称
	 */
	public static String get(Integer id) {
		return enumMaps.get(id).getState();
	}

	public int getCode() {
		return code;
	}

	public String getState() {
		return state;
	}

	/**
	 * 核心 规则编排查找器 策略
	 * 1,1,1 -> OrderCreate
	 *
	 * @return 状态执行器
	 */
	public static OrderStateRequest get(Map<Integer, StateRequest<Order>> typeState) {
		if (typeState.get(1).getStateValue() == OrderStatusEnum.CREATE.getCode()
				&& typeState.get(2) == null && typeState.get(3)== null) {
			return OrderStatusEnum.CREATE.getState();
		} else if (typeState.get(1).getStateValue() == OrderStatusEnum.CREATE.getCode()
				&& typeState.get(2).getStateValue() == PayStatusEnum.WAITING.getCode() && typeState.get(3)== null) {
			return PayStatusEnum.WAITING.getState();
		} else if (typeState.get(1).getStateValue() == OrderStatusEnum.CREATE.getCode()
				&& typeState.get(2).getStateValue() == PayStatusEnum.WAITING.getCode()
				&& typeState.get(3).getStateValue() == LogisticsStatusEnum.PICK.getCode()) {
			return LogisticsStatusEnum.PICK.getState();
		} else if (typeState.get(1).getStateValue() == OrderStatusEnum.FINISH.getCode()
				&& typeState.get(2).getStateValue() == PayStatusEnum.WAITING.getCode()
				&& typeState.get(3).getStateValue() == LogisticsStatusEnum.PICK.getCode()) {
			return OrderStatusEnum.FINISH.getState();
		} else {
			return OrderStatusEnum.CANCEL.getState();
		}
	}

}
