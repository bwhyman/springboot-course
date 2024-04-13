# spring-examples

### AOP
基于自定义注解/AOP切面的细粒度权限控制

### Transactions
spring托管事务，在spring容器捕获运行时异常时回滚  

### example06
整合自定义注解/AOP/Redis，模拟spring-cache-redis缓存框架的实现  
缓存默认5mins，支持在注解中指定时间及时间单位；ttl设为0，则永久缓存    

### Mock
通过Mock实现基于未完成组件的测试。

#### @MockBean

为修饰组件创建Mock对象，而非真实组件对象。

#### Mockito

when()，模拟Mock组件方法的调用  
anyX()，模拟调用Mock对象方法时传入参数  
thenReturn()，Mock组件方法调用时的返回值  
thenThrow()，Mock组件抛出指定异常对象  
doAnswer()，Mock组件方法调用时，执行操作  

UserRepository为未完成组件，测试用例中通过Mock模拟组件对象及方法调用。