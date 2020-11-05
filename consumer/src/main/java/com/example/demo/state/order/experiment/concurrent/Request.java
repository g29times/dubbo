package com.example.demo.state.order.experiment.concurrent;


public interface Request {

    void process();

    Long getDomainId();

}
