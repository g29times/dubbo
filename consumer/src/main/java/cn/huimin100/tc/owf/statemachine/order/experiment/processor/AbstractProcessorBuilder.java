package cn.huimin100.tc.owf.statemachine.order.experiment.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import javax.annotation.PostConstruct;

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
 * @date 2020/10/27 17:35
 * @see Object
 * @since 1.0
 */
public abstract class AbstractProcessorBuilder<T> {

    @Autowired
    protected AutowireCapableBeanFactory autowireCapableBeanFactory;

    protected AbstractProcessor<T> instance;

    @PostConstruct
    public void init() {
        initProcessor();
    }

    public abstract AbstractProcessorBuilder initProcessor();

    public AbstractProcessor<T> build() {
        return instance;
    }

    public void addLast(AbstractProcessor<T> processor) {
        if (instance == null) {
            instance = autowired(processor);
            return;
        }
        AbstractProcessor<T> next = instance;
        while (next.getNext() != null) {
            next = next.getNext();
        }
        next.setNext(autowired(processor));

    }

    protected AbstractProcessor<T> autowired(AbstractProcessor<T> processor) {
        // TODO 上线后解开
//        autowireCapableBeanFactory.autowireBean(processor);
        return processor;
    }

}
