### 项目说明
本项目是一个快速启动的dubbo脚手架，基于官方文档，以mvp模式支持了基本的注册和网络调用。

#### 整合Feign demo
使用
https://www.cnblogs.com/ncwuwsh/p/12732516.html  
解决坑（源码）：  
1. get方法，报Request method 'POST' not supported  
    consumer引入pom feign-httpclient  
2. 传参后服务端收到为null  
    1. 普通类型：client的feignApi增加@RequestParam  
    2. 对象类型：server的controller增加@RequestBody  
3. 不支持@GetMapping类似注解声明请求，需使用@RequestMapping  
https://www.cnblogs.com/dennyzhangdd/p/7978454.html  
https://blog.csdn.net/f641385712/article/details/82431502  
https://zhuanlan.zhihu.com/p/69641274

#### 参考
http://dubbo.apache.org/zh-cn/docs/user/references/protocol/dubbo.html  
https://github.com/alibaba/dubbo-spring-boot-starter  
https://github.com/apache/dubbo-spring-boot-project  
https://blog.csdn.net/qq_27834905/article/details/107790140  
https://my.oschina.net/yunduansing/blog/4467033

#### 注册中心
zk - 本地  
nacos - 阿里云

#### 测试
测试类：com.example.demo.controller.HelloController  
测试地址：http://localhost:8082/sayHello?name=World