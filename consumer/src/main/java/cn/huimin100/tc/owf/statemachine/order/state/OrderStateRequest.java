package cn.huimin100.tc.owf.statemachine.order.state;

import cn.huimin100.tc.owf.statemachine.order.StateRequest;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;

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
 * @description:
 * @date 2020/10/15 18:38
 * @see Object
 * @since 1.0
 */
public interface OrderStateRequest extends StateRequest<Order> {

    /**
     * 更新状态
     */
    void pre(Order order);

    default void after(Order order) {
        System.out.println(order);
    }

    /**
     * 更新逆向状态
     */
    void reverse(Order order);

    @Override
    default void process(Order order) {
        // 1 验证 - 2 处理 - 3 变更状态 - 4 通知
        if(check(order)) {
            pre(order);
            change(order);
            after(order);
        } else {
            System.out.println("检查不通过 - " + order);
        }
    }

    /**
     * 操作日志（快照）
     */
    default void log(Order order) {
        System.out.println(getContext() + " - " + order + " 订单操作日志已记录。");
    }
}
