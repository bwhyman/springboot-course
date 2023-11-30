# SpringMVC-Examples

### example03 Bean Validation
方法级校验，必须注入MethodValidationPostProcessor对象。  

### example05 JWT

### example06 Interceptor
声明拦截器后，需配置拦截规范。  
登录验证成功，将常用数据通过JWT加密后存于token，在登录拦截器解密后置于request，并注入controller组件使用。  

可将角色数据声明为随机字符串。  
```java
public static final String ADMIN = "U8gR5";
public static final String USER = "oI6pz";
```
仅在序列化时忽略数据。  
```java
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
```

### example07 Timer