package cn.huimin100.tc.owf.statemachine.order;

import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;

import java.util.ArrayList;
import java.util.List;

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
public interface StateRequest<T> extends Request<T> {

    /**
     * 获取可能来自的节点
     */
    default List<OrderStateRequest> getParents() {
         return new ArrayList<>();
    }

    /**
     * 检查
     */
    default boolean check(T domain) {
        return true;
    }

    /**
     * 前置
     */
    void pre(Order order);

    /**
     * 改状态
     */
    void change(T domain);

    /**
     * 后置
     */
    default void after(Order order) {
        System.out.println(order);
    }

    /**
     * 获取请求对应的上下文
     */
    RequestContext<T> getContext();

    /**
     * 设置请求的上下文
     */
    void setContext(RequestContext<T> context);

    /**
     * 获取业务主键
     */
    default Long getDomainId() {
        return getContext().getDomainId();
    }

    /**
     * 获取当前状态
     *
     * @return 1 Create 2 Finish 3 Return
     */
    int getStateValue();

    /**
     * 获取当前状态描述
     */
    String getStateDesc();

}
