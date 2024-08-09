# Experiments
### 实验1 持久化实验
**实验目的**  
掌握基于idea的基本springboot工程项目的创建方法。  
理解maven项目结构，依赖配置声明管理。  
掌握DO类与数据表的映射规则。  
掌握基本的属性映射规则。  
理解雪花算法主键生成策略与特点。  
掌握基于spring-data-jdbc框架的基本CURD操作。  

**实验内容**   
创建springboot项目，添加spring-data-jdbc框架相关依赖。  
创建springboot项目配置文件，添加数据源/logging等相关配置。  
创建数据表生成脚本，编写user表结构。  
打开idea database视图，添加连接远程数据库。  
执行脚本生成测试数据表。  
编写user表对应的DO类，按映射规则声明属性。  
编写操作DO类的Repository组件。  
编写测试类，注入Repository组件，执行增删改等持久化方法。  
测试事务  

### 实验2 关联查询映射实验
**实验目的**  
理解仅索引非外键关系的作用。  
掌握编写关联查询语句的方法。  
掌握关联查询的声明使用方法。  
掌握基于RowMapper/ResultSetExtractor接口的结果集映射方法。  

**实验内容**   
结合上一实验内容。  
在schema脚本添加user/address，声明若干字段，并添加至数据库。  
在address包含user表主键并设置为索引；user one-to-many address，address one-to-one user。  
编写repository接口。  
模拟1个user，对应2个address。  
按需创建不同方式封装user/address中信息的DTO类。  
实现以下查询

- 基于userid，查询全部address信息，通过address repository查询即可
- 基于addressid，查询address信息以及user信息，需DTO
- 基于userid，查询user信息，以及全部address信息，需DTO

编写测试类测试

### 实验3 AOP切面实验
**实验目的**
理解面向切面编程的作用与意义。  
理解动态代理技术。
掌握spring AOP框架的声明创建方法。  
掌握基本前置/后置/环绕等通知声明方法。  
掌握基本切面执行表达式的声明方法。  
掌握基于自定义注解/AOP切面技术的权限控制。  

**实验内容**   
创建springboot项目，添加aop依赖。  
声明日志等基本配置。  
创建自定义权限注解。  
编写业务组件，基于自定义权限注解模拟权限业务方法。  
创建基于自定义注解的权限切面类，声明基于注解的方法级/类级环绕拦截器，并模拟判断权限。
业务方法权限与请求用户权限不符则抛出自定义异常。  
编写测试类，测试业务组件的调用。

### 实验4 SpringMVC实验
**实验目的**  
理解并掌握REST API的设计及实现方法。  
理解并掌握controller组件的声明方法。  
理解并掌握HTTP GET/POST/PATCH请求的处理。  
理解并掌握JSON数据结构。   
理解并掌握基本基于Jackson的数据响应。   
理解并掌握基于json请求数据的封装。  
理解并掌握基于idea的HTTP REST API模拟请求脚本。  

**实验内容**  
创建一个支持SpringMVC的springboot项目。  
添加log/jackson忽略空属性等基本配置。  

需求0  
创建ResultVO类，统一处理响应数据。  
在controller下，创建UserController控制组件，添加REST注解，声明/api根路径。  
创建处理/index，请求方法，返回ResultVO对象，封装普通字符串即可。  

需求+1  
在dto下，创建User类，添加用户名/密码，为密码添加序列化忽略注解，当序列化user对象时，将忽略密码属性。  
```shell
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
```
在控制组件中模拟一个users集合，模拟封装若干user对象。  
创建处理/users，get请求方法，封装users集合。   
创建处理/users/{uid}，get请求方法，获取请求地址user ID参数，基于参数从users集合中获取对象，封装到返回。注意Map.of()不能添加null，因此需处理如果集合中没有指定ID对象的实现。  
创建处理/users，post请求方法，将请求数据封装到User对象，作为参数注入方法，将对象添加到users集合。  

在test下，创建http文件夹，创建user.http测试脚本测试请求。  

### 实验5 SpringMVC拦截器实验
**实验目的**  
理解拦截器的作用与意义。
掌握拦截器的声明配置方法。
掌握拦截器过滤指定请求的配置方法。
掌握拦截器的实现方法。
了解拦截器回调方法的区别和使用场景。
掌握在拦截器中请求权限验证。

**实验内容**  
创建springboot项目，引入Springmvc等依赖。

需求0  
添加Spring Security框架中的加密解密依赖。注意：不是在构建工程时添加Spring Security整体框架，仅需其中的crypto依赖
在application中添加log/jackson忽略空属性配置，以及自定义加密密钥与盐值。  

在component下，创建整合Jackson的加密/解密组件EncryptorComponent，注入密钥与盐值，实现对给定Map对象的加密/解密，对无法解密异常，转抛为自定义异常。  
创建SecurityConfiguration配置类，创建基于BCryptPasswordEncoder算法的PasswordEncoder对象，注入spring容器。  
在dto下创建User类，添加userName/password属性，getter/setter注解等；添加password的序列化忽略注解。  
在controller下，创建LoginController组件，注入密码编码组件，注入加密/解密组件。组件内创建一个Map，模拟基于用户名保存用户对象。  
创建处理/register post请求方法，将注册用户密码编码，将user对象保存在Map对象中(模拟数据库)。  
创建处理/login post请求方法，判断登录用户是否存在，用户密码是否正确；并将用户名加密保存在响应header中。  
在interceptor下，创建LoginInterceptor拦截器，从请求header中获取token数据，解密并将解密出的用户名置于requestattribute中。  

创建实现WebMvcConfigurer接口的配置实现类WebMvcConfiguration，重写addInterceptors()方法，注册拦截器，设置拦截规则过滤。  

在LoginController组件中，创建处理/index get请求，在方法中注入requestattribute中的用户名，并将用户名返回。

测试  
在test下，创建http目录，创建login.http测试脚本。  
编写注册测试脚本。  
编写登录测试脚本测试，在响应中获取token数据。  
编写/index请求测试脚本，在header中携带token发起请求。  

### 实验6 Cache缓存实验
**实验目的**  
理解业务层缓存的作用与意义。  
理解spring-cache缓存框架的实现原理。  
掌握spring-cache缓存框架的声明配置方法。  
掌握基本缓存注解的声明方法。  
掌握基本SpEL表达式使用方法。  

**实验内容**  
创建springboot项目，添加springmvc/cache等实验依赖，声明启动缓存。  
创建测试DTO类；创建测试模拟持久层组件，实现基本获取更新操作。  
创建业务组件，获取全部用户，获取指定用户，更新指定用户业务逻辑。   
添加spring-cache缓存注解，对以上业务使用合适缓存策略。  

### 实验7 Redis限流实验
**实验目的**  
理解非关系型数据库的特点及适用场景。  
理解redis基本数据类型。
掌握基本redis命令。
掌握lua脚本的基本语法。  
掌握redis函数的声明方法。  
掌握redisson客户端的声明配置方法。  
掌握redisson基本接口类型的使用方法。

**实验内容**
创建springboot项目，添加redisson等实验依赖。  
编写项目基本配置。  
编写redis数据源等基本redisson配置。  
编写lua脚本函数，实现数据的统计及时效性检验。  
编写应用启动监听器，注册脚本函数到redis服务器。 
编写测试类，通过redisson客户端调用调用redis函数，测试限流函数的有效性。


### 实验8 Redis stream消息队列实验
**实验目的**  
理解消息中间件的特点及适用场景。  
理解基于redis stream实现消息队列的原理。  
理解redis stream中消费组/消费者/消息状态/消息确认等基本概念。  
理解ULID分布式主键算法。
掌握spring-data-redis框架的基本使用方法。  
掌握基于redis的消息监听器的声明配置方法。 
掌握创建消息容器/消息消费者等对象的声明配置方法。  
掌握消息发送确认的方法。  

**实验内容**
基于上一实验内容。  
编写redis数据源等基本redisson配置。  
编写测试DTO类Order，添加所需属性及消息组等名称。  
编写lua脚本，实现针对指定商品的单线程抢购逻辑函数。  
编写消息监听器，确认并消费订单处理消息。  
编写创建消息监听器容器，绑定指定消息队列。   
编写业务组件，实现将抢购商品添加至redis服务器业务逻辑。 
实现抢购业务逻辑，通过调用redis函数实现在高并发下商品抢购的一致性操作，并在抢购成功后发送订单至消息队列处理。  
编写测试类，测试商品抢购逻辑。  
测试消息监听器的消息消费逻辑。  