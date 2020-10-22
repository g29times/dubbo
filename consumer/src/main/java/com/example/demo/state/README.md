状态机设计思路

第一版
java状态模式
    基于 Context + State  
解决问题：状态可拓展（通过State实现类）

第二版
策略模式
    Strategy  
解决问题：行为可拓展

第三版
流式处理
    借鉴Stream + Dubbo Context ThreadLocal  
解决问题：编程模型 + 循环依赖的代码
    
第四版
并发支持
    借鉴内存队列  
解决问题：把状态机做成服务器