package cn.huimin100.tc.owf.statemachine.order;

import cn.huimin100.tc.owf.statemachine.order.async.RequestAsyncProcessService;

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
 * @date 2020/10/22 11:36
 * @see Object
 * @since 1.0
 */
abstract class AbstractRequestContext<T> implements RequestContext<T> {

    private T domain;
    private StateRequest<T> state;
    private Request<T> request;

    @Override
    public RequestContext<T> getContext() {
        return this;
    }

    @Override
    public T getDomain() {
        return domain;
    }

    @Override
    public Long getDomainId() {
        return -1L;
    }

    @Override
    public RequestContext<T> setDomain(T domain) {
        this.domain = domain;
        return this;
    }

    @Override
    public StateRequest<T> getState() {
        return state;
    }

    @Override
    public RequestContext<T> setState(StateRequest<T> state) {
        this.state = state;
        return this;
    }

    @Override
    public RequestContext<T> getStrategy() {
        return this;
    }

    @Override
    public RequestContext<T> setStrategy(Request<T> request) {
        this.request = request;
        return this;
    }

    @Override
    public RequestAsyncProcessService getAsyncProcessor() {
        return null;
    }

    @Override
    public void request(T domain) {

    }

}
