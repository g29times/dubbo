package com.example.demo.state.order.context;

/**
 * @see org.springframework.context.Lifecycle
 */
public interface Lifecycle {
    void start();

    void stop();

    boolean isRunning();
}
