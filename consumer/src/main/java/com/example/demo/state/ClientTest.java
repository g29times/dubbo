package com.example.demo.state;

import com.example.demo.state.demo.ConcreteStateA;
import com.example.demo.state.demo.ConcreteStateB;
import com.example.demo.state.demo.Context;
import com.example.demo.state.demo.State;
import com.example.demo.state.order.ContextApi;
import com.example.demo.state.order.StateApi;
import com.example.demo.state.order.StrategyApi;
import com.example.demo.state.order.concurrent.ProcessorPool;
import com.example.demo.state.order.context.OrderContext;
import com.example.demo.state.order.domain.Order;
import com.example.demo.state.order.domain.OrderDto;
import com.example.demo.state.order.processor.OrderProcessorBuilder;
import com.example.demo.state.order.state.OrderCreateState;
import com.example.demo.state.order.state.OrderReverseState;
import com.example.demo.state.order.state.OrderState;
import com.example.demo.state.order.strategy.OrderCreateStrategy;
import com.example.demo.state.order.strategy.OrderReverseStrategy;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>
 * <a href="https://www.cnblogs.com/xyzq/p/11090344.html">状态模式</a>
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
//        orderV1();
//        orderV2();
//        orderV3();
//        orderV4();
        orderV5();
    }

    private static void orderV5() {
        // demo
//        getSubmitPreProcessorBuilder(bizType).build().invoke(context, ret);

        // order
        OrderContext orderFlow = new OrderContext();
        Order order = new Order();
        order.setId(1234L);

        new OrderProcessorBuilder().initProcessor().build().invoke(order);
        System.out.println(order);
    }

    private static void orderV4() {
        OrderContext/*ContextApi<Order>*/ orderFlow = new OrderContext();
        Order order = new Order();
        order.setId(1234L);

//        ProcessorPool orderPool = ProcessorPool.getInstance();
//        orderPool.start(orderFlow);
        ProcessorPool.start();
        order = orderFlow.of(order).fork(orderFlow::next).fork(orderFlow::next).getDomain();
        // TODO get future
//        System.out.println(order);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//
        orderFlow.peek();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        orderPool.stop();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private static void orderV3() {
        OrderContext/*ContextApi<Order>*/ orderFlow = new OrderContext();
        Order order = new Order();
        order.setId(1234L);

        // of(order) | order.create() | orderService.create(order);
//        order = orderFlow.of(order).push(OrderStrategy::process).push(OrderStrategy::process).get();
        order = orderFlow.of(order).push(orderFlow::next).push(orderFlow::next).getDomain();
        System.out.println(order);
        OrderDto dto = orderFlow.of(order).map(OrderDto::new).getDomain();
        System.out.println("TEST: " + dto);
    }
    private static class OrderStrategy {
        public static void process(Order order) {
            // TODO 需配置化
            OrderContext context = OrderContext.getOrderContext();
            OrderState create = context.getOrderCreate();
            create.update(order);
//        db.insert(order);
            create.next(order);
            create.log(order);
            System.out.println();
        }
    }

    private static void orderV2() {
        ContextApi<Order> orderFlow = new OrderContext();
        Order order = new Order();
        order.setId(1234L);
        orderFlow.setStrategy(new OrderCreateStrategy(orderFlow)).request(order);
        orderFlow.setStrategy(new OrderReverseStrategy(orderFlow)).request(order);
    }

    private static void orderV1() {
        ContextApi<Order> orderFlow = new OrderContext();
        Order order = new Order();

        StateApi<Order> create = new OrderCreateState(orderFlow);
        orderFlow.setState(create);
        StrategyApi<Order> createStrategy = new OrderCreateStrategy(orderFlow);
        orderFlow.setStrategy(createStrategy);
        orderFlow.request(order);
        System.out.println();

        StateApi<Order> reverse = new OrderReverseState(orderFlow);
        orderFlow.setState(reverse);
        StrategyApi<Order> reverseStrategy = new OrderReverseStrategy(orderFlow);
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
