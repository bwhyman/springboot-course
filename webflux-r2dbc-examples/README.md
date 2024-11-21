# webflux-r2dbc-examples

### Reactive Flux/Mono

Flux/Mono发布者  
map()/flatMap()/filter()/handle()/mapNotNull()/switchIfEmpty()/defaultIfEmpty()/Mono.defer()/onErrorResume()/Mono.error()/mapNotNull()/Flux.merge()/Mono.when()/Mono.just()/Flux.just()/then()/thenReturn()/Flux.from()/Flux.fromIterable()/cast()等方法。

### WebFlux

异步非阻塞Web框架，支持背压。  
webflux中禁止阻塞操作，所有方法必须返回Flux/Mono订阅，直到controller方法返回。用户发送到controller的请求即为消息订阅者。    
使用异步ServerHttpResponse/ServerHttpRequest接口替代Servlet接口，可直接通过@RequestAttribute注解注入controller方法。

### WebFilter

拦截器通过WebFilter接口实现  
拦截路径不再单独注册声明，默认全部拦截，需手动通过请求路径判断拦截/排除规则。  
通过order声明拦截顺序。

### WebExceptionHandler

在filter中抛出的异常无法在全局异常中捕获，必须通过WebExceptionHandler接口单独处理。  
因此，在拦截器校验失败后，不再抛出异常，而是直接响应错误数据。

### R2DBC

标准JDBC是阻塞的，通过r2dbc实现整合webflux的全非阻塞实现。  

### @EventListener

可通过注解实现应用多种事件的监听回调。可通过监听ApplicationReadyEvent事件在应用就绪后执行初始化数据的插入
