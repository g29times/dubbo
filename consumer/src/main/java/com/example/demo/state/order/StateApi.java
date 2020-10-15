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
public interface StateApi<T> {

    /**
     * 获取上下文
     */
    ContextApi<T> getContext();

    /**
     * 获取当前状态
     *
     * @return 1 Create 2 Finish 3 Return
     */
    int getState();

    void setState(int state);

    /**
     * 更新状态
     */
    void update(T t);

    /**
     * 逆向状态（退款）
     */
    void reverse(T t);

    /**
     * 下发
     */
    void inform(T t);

    /**
     * 操作日志（快照）
     */
    void log(T t);
}
