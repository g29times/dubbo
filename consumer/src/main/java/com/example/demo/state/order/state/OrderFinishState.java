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
 * @description: 完成
 * @date 2020/10/14 18:06
 * @see Object
 * @since 1.0
 */
public class OrderFinishState implements StateApi {

    private ContextApi context;

    public OrderFinishState(ContextApi context) {
        this.context = context;
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public ContextApi getContext() {
        return context;
    }
}
