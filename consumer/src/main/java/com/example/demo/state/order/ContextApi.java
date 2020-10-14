package com.example.demo.state.order;

public interface ContextApi {
    ContextApi getContext();
    void request(Order order);
    StateApi getState();
    void setState(StateApi state);
    StrategyApi getStrategy();
    void setStrategy(StrategyApi strategy);
}
