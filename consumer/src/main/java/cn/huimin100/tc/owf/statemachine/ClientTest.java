package cn.huimin100.tc.owf.statemachine;

import cn.huimin100.tc.owf.statemachine.demo.ConcreteStateB;
import cn.huimin100.tc.owf.statemachine.demo.Context;
import cn.huimin100.tc.owf.statemachine.order.ContextApi;
import cn.huimin100.tc.owf.statemachine.order.Request;
import cn.huimin100.tc.owf.statemachine.order.StateRequest;
import cn.huimin100.tc.owf.statemachine.order.async.ProcessorPool;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.experiment.strategy.OrderCreateRequest;
import cn.huimin100.tc.owf.statemachine.demo.ConcreteStateA;
import cn.huimin100.tc.owf.statemachine.demo.State;
import cn.huimin100.tc.owf.statemachine.order.context.OrderContext;
import cn.huimin100.tc.owf.statemachine.order.experiment.processor.OrderProcessorBuilder;
import cn.huimin100.tc.owf.statemachine.order.experiment.strategy.OrderCancelRequest;
import cn.huimin100.tc.owf.statemachine.order.state.order.OrderCancel;
import cn.huimin100.tc.owf.statemachine.order.state.order.OrderCreate;
import cn.huimin100.tc.owf.statemachine.order.state.OrderStateRequest;

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
//        /*orderContext = */OrderContext.getThreadContext(orderContext)/*.setDomain(orderContext)*/.process()/*.getDomain()*/;
//        System.out.println(orderContext);
//        OrderContext.getInstance().setDomain(orderContext).next().next()/*.fork(orderContext::next)*/.getDomain();
        OrderContext orderContext = new OrderContext(order2);
        orderContext = (OrderContext) orderContext.process().getContext();
        System.out.println("+++++++++++++++++++++++++" + orderContext.getDomain());
        try {
            Thread.sleep(1200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("+++++++++++++++++++++++++" + orderContext.getState());

        new OrderContext(order2).process();
        try {
            Thread.sleep(1200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new OrderContext(order2).process();
        try {
            Thread.sleep(1200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new OrderContext(order2).process();
        try {
            Thread.sleep(1200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        OrderContext.getInstance().peek();
//        OrderContext.getInstance().setDomain(orderContext).process();
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
        orderFlow.setStrategy(new OrderCreateRequest(orderFlow)).request(order);
        orderFlow.setStrategy(new OrderCancelRequest(orderFlow)).request(order);
    }

    private static void orderV1() {
//        ContextApi<Order> orderFlow = OrderContext.getInstance();
        Order order = new Order();
        ContextApi<Order> orderFlow = OrderContext.getThreadContext(order);

        StateRequest<Order> create = new OrderCreate(/*orderFlow*/);
        orderFlow.setState(create);
        Request<Order> createRequest = new OrderCreateRequest(orderFlow);
        orderFlow.setStrategy(createRequest);
        orderFlow.request(order);
        System.out.println();

        StateRequest<Order> reverse = new OrderCancel();
        orderFlow.setState(reverse);
        Request<Order> reverseRequest = new OrderCancelRequest(orderFlow);
        orderFlow.setStrategy(reverseRequest);
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
