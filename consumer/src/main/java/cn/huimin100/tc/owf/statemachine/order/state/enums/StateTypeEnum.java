package cn.huimin100.tc.owf.statemachine.order.state.enums;

import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;
import cn.huimin100.tc.owf.statemachine.order.state.order.OrderCancel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 核心 状态类型码枚举协调器
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
    ORDER(1, OrderStatusEnum.getEnumMaps()),

    /**
     * 支付
     */
    PAY(2, PayStatusEnum.getEnumMaps()),

    /**
     * 物流
     */
    LOGISTICS(3, LogisticsStatusEnum.getEnumMaps());

    StateTypeEnum(int code, Map<Integer, OrderStateRequest> state) {
        this.code = code;
        this.state = state;
    }

    private final int code;

    private final Map<Integer, OrderStateRequest> state;

    public int getCode() {
        return code;
    }

    public Map<Integer, OrderStateRequest> getState() {
        return state;
    }

    private static Map<Integer, OrderStateRequest> enumMaps = new HashMap<>();

    static {
        for (StateTypeEnum e : StateTypeEnum.values()) {
            e.getState().forEach(enumMaps::put);
        }
    }

    /**
     * 核心 规则编排查找器
     * 1,1,1 -> OrderCreate
     *
     * @return 状态执行器
     */
    public static OrderStateRequest get(Order order) {
        return get(null, order);
    }

    /**
     * 核心 规则编排查找器
     * 1,1,1 -> OrderCreate
     *
     * @return 状态执行器
     */
    public static OrderStateRequest get(List<OrderStateRequest> strategies, Order order) {
        return (strategies != null ? strategies : enumMaps.values()).stream().filter(s ->
                // TODO 重点配置 默认订单取消
                s.isHandler(order)).findFirst().orElse(new OrderCancel());
    }

}
