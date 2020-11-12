package cn.huimin100.tc.owf.client;

import cn.huimin100.tc.owf.statemachine.order.domain.Bill;

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

    public static void main(String[] args) {
        System.out.println(Math.random());
    }

    public Boolean addOrUpdate(Bill bill) {
        boolean result = Math.random() > 0.5;
        System.out.println("PayServiceApi addOrUpdate " + bill + " " + result);
        return result;
    }

}
