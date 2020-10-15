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
 * @description: 订单实体
 * @date 2020/10/14 18:13
 * @see Object
 * @since 1.0
 */
public class Order {

    private Long id;

    private StateApi<Order> state;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", state=" + state +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StateApi<Order> getState() {
        return state;
    }

    public void setState(StateApi<Order> state) {
        this.state = state;
    }
}
