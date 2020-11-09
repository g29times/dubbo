package com.example.demo.state.order.domain;

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
 * @description: 支付单
 * @date 2020/10/29 11:11
 * @see Object
 * @since 1.0
 */
public class Bill {

    private Long id;

    private int billState;

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", billState=" + billState +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBillState() {
        return billState;
    }

    public void setBillState(int billState) {
        this.billState = billState;
    }
}
