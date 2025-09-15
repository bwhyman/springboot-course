# springsecurity-examples

### Introductions

Spring Security在springmvc前执行，因此无法使用springmvc的统一异常处理，必须单独响应JSON数据。  
SecurityFilterChain声明配置，基于角色/路径的粗粒度权限校验，以及自定义过滤器。  
在控制层方法，实现细粒度方法级权限校验。  
在控制层方法，直接注入jwt解析并置于SecurityContext的Authentication数据。  
