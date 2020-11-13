package cn.huimin100.tc.owf.statemachine.order.state.enums;

import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;
import cn.huimin100.tc.owf.statemachine.order.state.pay.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付业务状态码
 *
 * @author litong
 * @program:
 * @description:
 * @create: 2020-11-3
 */
public enum PayStatusEnum {
    /**
     * 待支付
     */
    WAITING(21, new PayWaiting()),
    /**
     * 支付中
     */
    PAYING(22, new Paying()),
    /**
     * 支付成功
     */
    PAYED(231, new Payed()),
    /**
     * 待收款
     */
    PENDING(232, new PayPending()),
    /**
     * 司机回款
     */
    DRIVER(241, new PayDriver()),
    /**
     * 仓库回款
     */
    WAREHOUSE(242, new PayWarehouse()),
    /**
     * 已核销
     */
    PAY_OFF(25, new PayOff()),

    /**
     * 退款中
     */
    REFUND(261, new PayRefund()),
    /**
     * 退款失败
     */
    REFUND_FAIL(262, new PayRefundFail()),
    /**
     * 退款成功
     */
    REFUND_SUCCESS(263, new PayRefundSuccess()),
    /**
     * 支付取消
     */
    CANCEL(27, new PayCancel());

    PayStatusEnum(int code, OrderStateRequest state) {
        this.code = code;
        this.state = state;
    }

    private final int code;

    private final OrderStateRequest state;

    private static Map<Integer, PayStatusEnum> enumMaps = new HashMap<>();

    static {
        for (PayStatusEnum e : PayStatusEnum.values()) {
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
