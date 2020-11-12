package cn.huimin100.tc.owf.statemachine.order.context;

/**
 * @see org.springframework.context.Lifecycle
 */
public interface Lifecycle {
    void start();

    void stop();

    boolean isRunning();
}
