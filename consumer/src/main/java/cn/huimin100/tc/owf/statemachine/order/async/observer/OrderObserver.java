package cn.huimin100.tc.owf.statemachine.order.async.observer;

import cn.huimin100.tc.owf.client.PayServiceApi;
import cn.huimin100.tc.owf.statemachine.order.domain.Bill;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;

import java.util.Observable;
import java.util.Observer;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>
 * <a href="https://www.cnblogs.com/java-my-life/archive/2012/05/16/2502279.html">google</a>
 * https://blog.csdn.net/weixin_39035120/article/details/86225377
 *
 * @author li tong
 * @description: 观察者实例
 * @date 2020/09/10 13:27
 * @see Object
 * @since 1.0
 */
public class OrderObserver implements Observer {

    private PayServiceApi payServiceApi;

    public OrderObserver(Observable observable) {
        observable.addObserver(this);
    }

    /**
     * 1 观察者方式
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        Order order = (Order) o;
        System.out.print("Heard Order update state=" + order.getState1() + ", ");
        // 推模式
//        System.out.println("Subject update..." + o + ", state=" + arg);

        payServiceApi = new PayServiceApi();
        payServiceApi.addOrUpdate(new Bill());
    }

    /**
     * 2 event方式
     */
    public static void listen() {
        ApplicationContext.addListener(event -> {
            if(event.getSource() instanceof Order) {
                Order order = (Order) event.getSource();
                System.out.println(System.currentTimeMillis() + " 监听到：订单状态" + order);
                System.out.println();
            }
        });
    }

}
