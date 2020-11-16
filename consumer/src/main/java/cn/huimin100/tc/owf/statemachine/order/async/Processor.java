package cn.huimin100.tc.owf.statemachine.order.async;

import cn.huimin100.tc.owf.statemachine.order.StateRequest;
import cn.huimin100.tc.owf.statemachine.order.async.observer.ApplicationContext;
import cn.huimin100.tc.owf.statemachine.order.async.observer.ApplicationEvent;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.context.OrderRequestContext;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

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
 * @description: 请求处理线程
 * @date 2020/10/22 16:31
 * @see Object
 * @since 1.0
 */
public class Processor implements Callable<Order> {

	private final BlockingQueue<StateRequest<Order>> queue;

	public Processor(BlockingQueue<StateRequest<Order>> queue) {
		this.queue = queue;
		RequestAsyncProcessServiceImpl.getInstance().put(queue, this);
	}

//    private Future<Order> result;
//
//    public Future<Order> getResult() {
//        return result;
//    }
//
//    public void setResult(Future<Order> result) {
//        this.result = result;
//    }

	@Override
	public Order call() throws Exception {
		System.out.println("Thread " + Thread.currentThread().getName() + " is running");
		Order order = null;
		try {
			while (true) {
				StateRequest<Order> stateRequest = queue.take();
//				System.out.println("Thread " + Thread.currentThread().getName() + " Processor: request: " + stateRequest);

				OrderRequestContext orderContext = (OrderRequestContext) stateRequest.getContext();
//                System.out.println("Thread " + Thread.currentThread().getName() + " Processor: context: " + orderContext);

				order = orderContext.getDomain();
//				System.out.println("Thread " + Thread.currentThread().getName() + " Processor: order: " + order);

				// 模拟耗时
				Thread.sleep(500);
				System.out.println(stateRequest.getClass() + " is processing");
				stateRequest.process(order);
				Thread.sleep(500);

				ApplicationContext.publishEvent(new ApplicationEvent(order));
			}
		} catch (Exception e) {
			// 线程内异常捕获 Future https://blog.csdn.net/zangdaiyang1991/article/details/89228103
			e.printStackTrace();
		}
		return order;
	}

}
