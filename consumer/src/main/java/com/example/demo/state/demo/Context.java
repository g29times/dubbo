package com.example.demo.state.demo;

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
 * @date 2020/10/14 16:55
 * @see Object
 * @since 1.0
 */
public class Context {
    /**
     * 持有一个State类型的对象实例
     */
    private State state;

    public void setState(State state) {
        this.state = state;
    }

    /**
     * 用户感兴趣的接口方法
     */
    public void request(String sampleParameter) {
        // 转调state来处理
        state.method1(sampleParameter);
        state.method2(sampleParameter);
    }
}
