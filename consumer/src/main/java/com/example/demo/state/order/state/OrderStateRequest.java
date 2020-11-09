package com.example.demo.state.order.state;

import com.example.demo.state.order.StateRequest;
import com.example.demo.state.order.domain.Order;

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

    @Override
    default Long getDomainId() {
        return getContext().getDomainId();
    }

    /**
     * 更新状态
     */
    void update(Order order);

    /**
     * 更新逆向状态
     */
    void reverse(Order order);

    /**
     * 操作日志（快照）
     */
    default void log(Order order) {
        System.out.println(getContext() + " - " + order + " 操作日志已记录。");
    }
}
