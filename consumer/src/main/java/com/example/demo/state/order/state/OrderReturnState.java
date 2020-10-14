package com.example.demo.state.order.state;

import com.example.demo.state.order.ContextApi;
import com.example.demo.state.order.StateApi;

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
 * @description: 退款单
 * @date 2020/10/14 18:09
 * @see Object
 * @since 1.0
 */
public class OrderReturnState implements StateApi {

    private ContextApi context;

    public OrderReturnState(ContextApi context) {
        this.context = context;
    }

    @Override
    public int getState() {
        return 3;
    }

    @Override
    public ContextApi getContext() {
        return context;
    }
}
