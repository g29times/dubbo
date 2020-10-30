package com.example.demo.state.order.client.down;

import com.example.demo.state.order.domain.Bill;
import org.apache.commons.lang3.RandomUtils;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>模拟支付服务
 * <a href="www.google.com">google</a>
 *
 * @author li tong
 * @description:
 * @date 2020/10/29 11:02
 * @see Object
 * @since 1.0
 */
public class PayServiceApi {

    public Boolean addOrUpdate(Bill bill) {
        boolean result = RandomUtils.nextBoolean();
        System.out.println("PayServiceApi addOrUpdate " + bill + " " + result);
        return result;
    }

}
