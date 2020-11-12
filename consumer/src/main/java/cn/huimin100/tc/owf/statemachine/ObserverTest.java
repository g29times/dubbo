package cn.huimin100.tc.owf.statemachine;

import cn.huimin100.tc.owf.client.OrderServiceApi;
import cn.huimin100.tc.owf.statemachine.order.async.observer.OrderObserver;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;

import java.util.Observer;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>模拟App客户端
 * <a href="www.google.com">google</a>
 *
 * @author li tong
 * @description:
 * @date 2020/10/29 11:01
 * @see Object
 * @since 1.0
 */
public class ObserverTest {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    public static void test3() {
        OrderObserver.listen();

        OrderServiceApi orderServiceApi = new OrderServiceApi();
        Order order = new Order();
        order.setId(1234L);
        order.setState(1);
        orderServiceApi.addOrUpdate(order);
        order.setState(2);
    }

    /**
     * 使用观察者设计模式
     * 限制
     * 1 需 new 大量的 Observer
     * 2 Order 的 set 方法需改造
     *
     * @see OrderObserver 观察者
     */
    public static void test2() {
        OrderServiceApi orderServiceApi = new OrderServiceApi();
        Order order = new Order();
        Observer observer = new OrderObserver(order);
        order.setId(1234L);
        order.setState(1);
        orderServiceApi.addOrUpdate(order);
        order.setState(2);
        orderServiceApi.addOrUpdate(order);
    }

    // 现有做法
    public static void test1() {
        OrderServiceApi orderServiceApi = new OrderServiceApi();
        Order order = new Order();
        order.setId(1234L);
        order.setState(1);
        orderServiceApi.addOrUpdate(order);
        order.setState(2);
        orderServiceApi.addOrUpdate(order);
    }

}
