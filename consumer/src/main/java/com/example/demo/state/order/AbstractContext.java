package com.example.demo.state.order;

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
abstract class AbstractContext<T> implements ContextApi<T> {

    private T domain;
    private StateApi<T> state;
    private Strategy<T> strategy;

    @Override
    public void request(T domain) {

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
    public ContextApi<T> setDomain(T domain) {
        this.domain = domain;
        return this;
    }

    @Override
    public StateApi<T> getState() {
        return state;
    }

    @Override
    public ContextApi<T> setState(StateApi<T> state) {
        this.state = state;
        return this;
    }

    @Override
    public Strategy<T> getStrategy() {
        return strategy;
    }

    @Override
    public ContextApi<T> setStrategy(Strategy<T> strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public RequestAsyncProcessService getAsyncProcessor() {
        return null;
    }

}
