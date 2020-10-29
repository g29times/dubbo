package com.example.demo.state.order.processor;

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
 * @date 2020/10/27 17:19
 * @see Object
 * @since 1.0
 */
public abstract class AbstractProcessor<T> implements Processor<T> {

    // TODO 树型
    private AbstractProcessor<T> next = null;

    public AbstractProcessor<T> getNext() {
        return next;
    }

    @Override
    public boolean check(T result) {
        return true;
    }

    public void setNext(AbstractProcessor<T> next) {
        this.next = next;
    }

    public void invoke(T result) {
        try {
            process(/*context, */result);
            fireNext(/*context, */result);
        } catch (Exception/*ProcessorInterruptedException*/ ex) {
//            return;
//        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public void fireNext(T result) {
        if (next != null) {
            if (check(/*context, */result)) {
                next.invoke(/*context, */result);
            } else { // TODO 这里不一定继续往下 可能等待
                if (next.getNext() != null) {
                    next.getNext().invoke(/*context, */result);
                }
            }
        }
    }

}
