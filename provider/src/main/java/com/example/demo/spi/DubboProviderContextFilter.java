package com.example.demo.spi;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author li tong
 * @date 2020/9/23 16:03
 * @see Class DubboLog
 * @see Class MediaDubboConsumerContextFilter
 * @since 1.0
 */
@Activate(group = {CommonConstants.PROVIDER}/*, order = -9999*/)
public class DubboProviderContextFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(DubboProviderContextFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        logger.info("【DUBBO Interceptor】{}->{}", context.getLocalAddressString(), context.getRemoteAddress());
        return invoker.invoke(invocation);
    }
}
