package com.example.demo.state.order.client.observer;

import java.util.EventListener;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>
 * 事件监听器
 *
 * @author li tong
 * @date 2019/7/26 10:57
 * @see org.springframework.context.ApplicationListener
 * @since 1.0
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

  /**
   * 监听事件
   *
   * @param event 事件
   */
  void handleEvent(E event);

}
