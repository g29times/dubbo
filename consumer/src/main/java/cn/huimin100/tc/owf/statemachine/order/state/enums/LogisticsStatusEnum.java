package cn.huimin100.tc.owf.statemachine.order.state.enums;

import cn.huimin100.tc.owf.statemachine.order.state.logistics.*;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 仓配业务状态码
 *
 * @author litong
 * @program:
 * @description:
 * @create: 2020-11-3
 */
public enum LogisticsStatusEnum {
    /**
     * 未配货
     */
    WAITING(31, new LogisticsWaiting()),
    /**
     * 已拣货
     */
    PICK(32, new LogisticsPick()),
    /**
     * 已出库
     */
    OUTBOUND(33, new LogisticsOutbound()),
    /**
     * 已发车
     */
    DEPART(34, new LogisticsDepart()),
    /**
     * 二次拉回
     */
    SECOND(35, new LogisticsSecond()),
    /**
     * 确认送达
     */
    CONFIRM(36, new LogisticsConfirm()),

    /**
     * 已取消
     */
    CANCEL(37, new LogisticsCancel());

    LogisticsStatusEnum(int code, OrderStateRequest state) {
        this.code = code;
        this.state = state;
    }

    private final int code;

    private final OrderStateRequest state;

    private static Map<Integer, OrderStateRequest> enumMaps = new HashMap<>();

    static {
        for (LogisticsStatusEnum e : LogisticsStatusEnum.values()) {
            enumMaps.put(e.getCode(), e.getState());
        }
    }

    public static Map<Integer, OrderStateRequest> getEnumMaps() {
        return enumMaps;
    }

    /**
     * 根据id查询名称
     *
     * @param id id
     * @return 名称
     */
    public static OrderStateRequest get(Integer id) {
        return enumMaps.get(id);
    }

    public int getCode() {
        return code;
    }

    public OrderStateRequest getState() {
        return state;
    }

}
