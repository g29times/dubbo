package com.example.demo.state;

import com.example.demo.state.demo.ConcreteStateA;
import com.example.demo.state.demo.ConcreteStateB;
import com.example.demo.state.demo.Context;
import com.example.demo.state.demo.State;
import com.example.demo.state.order.*;
import com.example.demo.state.order.StateApi;
import com.example.demo.state.order.state.OrderCreateState;
import com.example.demo.state.order.state.OrderReturnState;
import com.example.demo.state.order.strategy.OrderCreateStrategy;
import com.example.demo.state.order.strategy.OrderReverseStrategy;
import com.example.demo.state.order.StrategyApi;

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
 * @date 2020/10/14 16:57
 * @see Object
 * @since 1.0
 */
public class ClientTest {

    public static void main(String[] args) {
//        demo();
        order();
    }

    private static void order() {
        ContextApi orderFlow = new OrderWorkFlow();
        Order order = new Order();

        StateApi create = new OrderCreateState(orderFlow);
        orderFlow.setState(create);
        StrategyApi createStrategy = new OrderCreateStrategy(orderFlow);
        orderFlow.setStrategy(createStrategy);
        orderFlow.request(order);
        System.out.println();

        StateApi reverse = new OrderReturnState(orderFlow);
        orderFlow.setState(reverse);
        StrategyApi reverseStrategy = new OrderReverseStrategy(orderFlow);
        orderFlow.setStrategy(reverseStrategy);
        orderFlow.request(order);
    }

    private static void demo() {
        //创建环境
        Context context = new Context();
        //创建状态
        State stateA = new ConcreteStateA();
        //将状态设置到环境中
        context.setState(stateA);
        //请求
        context.request("common flow");
        //创建状态
        State stateB = new ConcreteStateB();
        //将状态设置到环境中
        context.setState(stateB);
        //请求
        context.request("common flow");
    }
}
