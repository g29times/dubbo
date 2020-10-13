package com.example.demo.spi;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

/**
 * @author li tong
 * @date 2020/9/23 16:03
 * @see Class DubboLog
 * @since 1.0
 */
@Activate(group = {CommonConstants.CONSUMER}/*, order = -9999*/)
public class DubboConsumerContextFilter implements Filter {

    public static final String LOGBACK_MDC_MARK = "MDC";

    private static final Logger logger = LoggerFactory.getLogger(DubboConsumerContextFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
//        Map<String, String> map = new HashMap<>();
//        map.putIfAbsent("name", "Hello, this is service<ID>" + MDC.get(LOGBACK_MDC_MARK));
//        context.setAttachments(map);
        logger.info("【DUBBO Interceptor】{}->{}", context.getLocalAddressString(), context.getRemoteAddress());
        return invoker.invoke(invocation);
    }
}
