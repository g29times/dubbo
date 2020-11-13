package cn.huimin100.tc.owf.statemachine.order.state.enums;

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

}
