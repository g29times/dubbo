package com.example.demo.spi.demo;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.junit.jupiter.api.Test;

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
 * @date 2020/10/13 17:20
 * @see Object
 * @since 1.0
 */
public class DubboSPITest {

    @Test
    public void sayHello() throws Exception {
        ExtensionLoader<Robot> extensionLoader =
                ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();

        ExtensionLoader<LoadBalance> extensionLoadBalance =
                ExtensionLoader.getExtensionLoader(LoadBalance.class);
        LoadBalance devLoadBalance = extensionLoadBalance.getExtension("dev");
        devLoadBalance.select(null, null, null);
    }

}
