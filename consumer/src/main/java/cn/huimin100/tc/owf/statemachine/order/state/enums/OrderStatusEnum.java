package cn.huimin100.tc.owf.statemachine.order.state.enums;

import cn.huimin100.tc.owf.statemachine.order.state.order.*;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单业务状态码
 *
 * @author litong
 * @program:
 * @description:
 * @create: 2020-11-3
 */
public enum OrderStatusEnum {
    /**
     * 订单创建
     */
    CREATE(11, new OrderCreate()),
    /**
     * 订单待审核
     */
    AUDITING(12, new OrderAuditing()),
    /**
     * 订单已锁定
     */
    LOCKED(131, new OrderLocked()),
    /**
     * 订单已审核
     */
    AUDITED(132, new OrderAudited()),
    /**
     * 订单暂缓下发
     */
    SUSPENDED(141, new OrderSuspended()),
    /**
     * 订单仓库生产中
     */
    WAREHOUSING(142, new OrderWarehousing()),
    /**
     * 订单配送中
     */
    TRANSPORTING(15, new OrderTransporting()),
    /**
     * 订单已送达
     */
    DELIVERED(16, new OrderDelivered()),
    /**
     * 订单完成
     */
    FINISH(17, new OrderFinish()),

    /**
     * 订单取消
     */
    CANCEL(18, new OrderCancel());

    OrderStatusEnum(int code, OrderStateRequest state) {
        this.code = code;
        this.state = state;
    }

    private final int code;

    private final OrderStateRequest state;

    private static Map<Integer, OrderStatusEnum> enumMaps = new HashMap<>();

    static {
        for (OrderStatusEnum e : OrderStatusEnum.values()) {
            enumMaps.put(e.getCode(), e);
        }
    }

    /**
     * 根据id查询名称
     *
     * @param id id
     * @return 名称
     */
    public static OrderStateRequest get(Integer id) {
        return enumMaps.get(id).getState();
    }

    public int getCode() {
        return code;
    }

    public OrderStateRequest getState() {
        return state;
    }

}
