package com.example.demo.state.order.client.up;

import com.example.demo.state.demo.ConcreteStateA;
import com.example.demo.state.demo.ConcreteStateB;
import com.example.demo.state.demo.Context;
import com.example.demo.state.demo.State;
import com.example.demo.state.order.ContextApi;
import com.example.demo.state.order.StateRequest;
import com.example.demo.state.order.Strategy;
import com.example.demo.state.order.context.OrderContext;
import com.example.demo.state.order.domain.Order;
import com.example.demo.state.order.experiment.concurrent.ProcessorPool;
import com.example.demo.state.order.experiment.processor.OrderProcessorBuilder;
import com.example.demo.state.order.experiment.strategy.OrderCancelStrategy;
import com.example.demo.state.order.experiment.strategy.OrderCreateStrategy;
import com.example.demo.state.order.state.OrderCancelStateRequest;
import com.example.demo.state.order.state.OrderCreateStateRequest;
import com.example.demo.state.order.state.OrderStateRequest;
import com.example.demo.state.order.state.OrderStatusEnum;

import java.util.HashMap;
import java.util.Map;

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

    public static class DB {
        private DB() {
        }

        private Map<Long, Order> orderTable = new HashMap<>();

        public Order getOrder(Long id) {
            return orderTable.get(id);
        }

        public void setOrder(Order order) {
            orderTable.put(order.getId(), order);
        }

        private static class Singleton {
            static final DB db = new DB();
        }

        private static DB getDB() {
            return Singleton.db;
        }
    }

    public static DB getDB() {
        return DB.getDB();
    }

    public static void main(String[] args) {
//        demo();

//        orderV1();
//        orderV2();

//        orderV3();
        orderV4();

//        orderV5();
//        orderV6();
    }

    private static void orderV6() {
        Order order = new Order();
        order.setId(1234L);
        System.out.println(createOrderV6(order));

        Order order1 = new Order();
        order1.setId(1234L);
        order1.setState(2);
        System.out.println(updateOrderV6(order1));
    }

    private static Order createOrderV6(Order order) {
        DB db = DB.getDB();
        db.setOrder(order);
        return order;
    }

    private static Order updateOrderV6(Order order) {
        DB db = DB.getDB();
        Order orderDB = db.getOrder(order.getId());
        orderDB.setState(order.getState());
        db.setOrder(orderDB);
        return orderDB;
    }

    private static void orderV5() {
        // demo
//        getSubmitPreProcessorBuilder(bizType).build().invoke(context, ret);

        // order
        Order order = new Order();
        order.setId(1234L);
        OrderContext orderFlow = OrderContext.getThreadContext(order);

        new OrderProcessorBuilder().initProcessor().build().invoke(order);
        System.out.println(order);
    }

    private static void orderV4() {
        ProcessorPool.start();

//        Order order1 = new Order();
//        order1.setId(123L);
//        // TODO 1 验证 状态跳号 (数据库) 2 幂等
//        order1.setState(OrderStatusEnum.PAY.getCode());
//        new OrderContext(order1/* -> request*/).process();

        Order order2 = new Order();
        order2.setId(124L);
        new OrderContext(order2).process();

//        /*order = */OrderContext.getThreadContext(order)/*.setDomain(order)*/.process()/*.getDomain()*/;
//        System.out.println(order);
//        OrderContext.getInstance().setDomain(order).next().next()/*.fork(orderContext::next)*/.getDomain();
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new OrderContext(order2).process();
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new OrderContext(order2).process();
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new OrderContext(order2).process();
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        OrderContext.getInstance().peek();
//        OrderContext.getInstance().setDomain(order).process();
//        ProcessorPool.stop();
//        System.gc();
    }

    private static void orderV3() {
        Order order = new Order();
        order.setId(1234L);
        OrderContext orderFlow = OrderContext.getThreadContext(order);
//        order.setState(OrderStatusEnum.PAY.getCode());

        // of(order) | order.create() | orderService.create(order);
//        order = orderFlow.setDomain(order).push(OrderStrategy::process).push(OrderStrategy::process).get();
//        order = orderFlow.setDomain(order).push(orderFlow::next).push(orderFlow::next).getDomain();
        System.out.println("print --- " + order);
//        OrderDto dto = orderFlow.setDomain(order).map(OrderDto::new).getDomain();
//        System.out.println("TEST: " + dto);
    }

    private static class OrderStrategyV3 {
        public static void process(Order order) {
            // TODO 需配置化
            OrderContext context = OrderContext.getThreadContext(order);
            OrderStateRequest create = context.getOrderCreate();
            create.update(order);
//        db.insert(order);
            create.next(order);
            create.log(order);
            System.out.println();
        }
    }

    private static void orderV2() {
//        ContextApi<Order> orderFlow = OrderContext.getInstance();
        Order order = new Order();
        order.setId(1234L);
        ContextApi<Order> orderFlow = OrderContext.getThreadContext(order);
        orderFlow.setStrategy(new OrderCreateStrategy(orderFlow)).request(order);
        orderFlow.setStrategy(new OrderCancelStrategy(orderFlow)).request(order);
    }

    private static void orderV1() {
//        ContextApi<Order> orderFlow = OrderContext.getInstance();
        Order order = new Order();
        ContextApi<Order> orderFlow = OrderContext.getThreadContext(order);

        StateRequest<Order> create = new OrderCreateStateRequest(/*orderFlow*/);
        orderFlow.setState(create);
        Strategy<Order> createStrategy = new OrderCreateStrategy(orderFlow);
        orderFlow.setStrategy(createStrategy);
        orderFlow.request(order);
        System.out.println();

        StateRequest<Order> reverse = new OrderCancelStateRequest();
        orderFlow.setState(reverse);
        Strategy<Order> reverseStrategy = new OrderCancelStrategy(orderFlow);
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
