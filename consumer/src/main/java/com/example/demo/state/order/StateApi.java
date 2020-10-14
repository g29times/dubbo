package com.example.demo.state.order;

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
 * @date 2020/10/14 17:03
 * @see Object
 * @since 1.0
 */
public interface StateApi {

    /**
     * 获取上下文
     */
    ContextApi getContext();

    /**
     * 获取当前状态
     *
     * @return 1 Create 2 Finish 3 Return
     */
    int getState();

    /**
     * 更新状态
     */
    default void update(Order order) {
        System.out.println(getContext().toString() + "_" + order.hashCode() + "更新订单：" + getState());
    }

    /**
     * 逆向状态（退款）
     */
    default void reverse(Order order) {
        System.out.println(getContext().toString() + "_" + order.hashCode() + "逆向订单：" + getState());
    }

    /**
     * 下发
     */
    default void inform(Order order) {
        System.out.println(getContext().toString() + "_" + order.hashCode() + "订单下发：" + getState());
    }

    /**
     * 操作日志（快照）
     */
    default void log(Order order) {
        System.out.println(getContext().toString() + "_" + order.hashCode() + "操作日志：" + getState());
    }
}
