package cn.huimin100.tc.owf.statemachine.order.state;

import java.util.HashMap;
import java.util.Map;

/**
 * 预定义的业务状态码
 *
 * @author litong
 * @program:
 * @description:
 * @create: 2020-11-3
 */
public enum OrderStatusEnum {
    /**
     * 创建订单
     */
    CREATE(11, new OrderCreateStateRequest()),

    /**
     * 取消订单
     */
    CANCLE(10, new OrderCancelStateRequest()),

    /**
     * 完成订单
     */
    FINISH(13, new OrderFinishStateRequest()),

    /**
     * 已支付订单
     */
    PAY(21, new PayCreate()),

    /**
     * 储运已接单
     */
    LOGISTICS(31, new LogisticsCreate());

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
