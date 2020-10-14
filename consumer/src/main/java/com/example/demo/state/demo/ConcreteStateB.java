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
 * @date 2020/10/14 16:57
 * @see Object
 * @since 1.0
 */
public class ConcreteStateB implements State {
    @Override
    public int getState() {
        return 2;
    }

    @Override
    public void method1(String sampleParameter) {
        System.out.println("ConcreteStateB=" + getState() + " method1 ：" + sampleParameter);
    }

    @Override
    public void method2(String sampleParameter) {
        System.out.println("ConcreteStateB=" + getState() + " method2 ：" + sampleParameter);
    }

}
