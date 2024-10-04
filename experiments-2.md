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
创建springboot项目，添加spring-data-jdbc框架相关依赖，整理项目资源。  
创建springboot项目配置文件，添加数据源/logging等相关配置。  
创建数据表生成脚本，编写user表结构。  
打开idea database视图，添加连接远程数据库。  
执行脚本生成测试数据表。  
编写user表对应的DO类，按映射规则声明属性。  
编写雪花算法注入实现。  
编写操作DO类的Repository组件。  
编写测试类，注入Repository组件，执行增删改等持久化方法。  
测试事务   

### 实验2 关联查询映射实验

**实验目的**  
理解仅索引非外键关系的作用。  
掌握编写关联查询语句的方法。  
掌握关联查询的声明使用方法。  
掌握基于Explain的SQL语句分析与优化。  
掌握基于RowMapper/ResultSetExtractor接口的结果集映射方法。  

**实验内容**   
基于上一实验内容。  
在schema脚本添加user/address数据表，声明若干字段。  
在address包含user表主键并设置为索引；user 1:N address，address 1:1 user。  
项目声明数据源/启动执行schema脚本/logging等基本配置。  
编写持久层组件userRepository/AddressRepository接口。  
编写测试用例，添加若干user记录，以及user对应的address记录。  

实现以下数据检索，且需先通过explain分析预执行的SQL语句，验证数据检索方式是否符合预期后再在组件实现。

- 基于userid，查询全部address信息，通过address repository查询即可
- 基于addressid，查询address信息以及user信息，通过RowMapper映射行实现
- 基于userid，查询user信息，以及全部address信息，通过ResultSetExtractor映射全部结果集实现
- 查询所有用户姓名及对应address的个数，正序排序。**说明，此需求仅用于练习，实际查询时不应出现没有分页的全表扫描**

按需创建不同方式封装信息的DTO类。  

编写测试类测试

### 实验3 SpringMVC实验

**实验目的**  
理解并掌握REST API的设计及实现方法。  
理解并掌握controller组件的声明方法。  
理解并掌握HTTP GET/POST/PATCH请求的处理。  
理解并掌握JSON数据结构。   
理解并掌握基本基于Jackson的数据响应。   
理解并掌握基于json请求数据的封装。  
理解并掌握基于idea的HTTP REST API模拟请求脚本。  

**实验内容**  
创建springboot项目，添加springmvc框架相关依赖，整理项目资源。  
添加log/jackson忽略空属性等基本配置。  

在exception包下，自定义枚举类型通用异常业务码。  
在vo包下，创建ResultVO类，统一封装通用响应数据，包括：响应数据/响应空数据/异常业务数据等。  

在dox包下，创建User类，添加id/name/account/password/createTime等属性。  
在service包下，创建UserService组件，模拟一个包含若干user对象的集合

- 获取全部users集合对象业务方法，listUsers()
- 基于account获取user对象业务方法，getUserByAccount()

在controller包下，创建LoginController控制组件,`/api/`，添加基本组件声明，注入UserService组件。

- 创建处理`login`路径POST请求方法login()，获取user对象，实现登录校验

在controller包下，创建AdminController控制组件,`/api/admin/`，添加基本组件声明

- 创建处理`users`路径GET请求方法getUsers()方法，获取全部用户信息

- 创建处理`users/{account}`路径GET请求方法getUser()方法，获取路径参数，调用业务组件查询

在test下创建http目录，创建test.http测试脚本，编写请求测试用例。

### 实验4 SpringMVC拦截器与安全实验

**实验目的**  
理解拦截器的作用与意义。
掌握拦截器的声明配置方法。
掌握拦截器过滤指定请求的配置方法。
掌握拦截器的实现方法。
了解拦截器和使用场景。
掌握在拦截器中请求权限校验的方法。 

**实验内容**  
基于上一实验内容。  
添加spring-security-crypto/java-jwt依赖。  
扩展配置，自定义token密钥。  

在exception包下，自定义unchecked异常，包括处理通用业务异常，以及自定义异常信息。  
在controller包下，创建ExceptionController组件，统一处理异常信息。  
在component包下，创建PasswordEncoderConfig配置类，创建基于BCryptPasswordEncoder算法的PasswordEncoder组件。
在component包下，创建JWTComponent组件，注入配置中密钥，实现token的加密/解密。  

扩展User类，追加role属性，以及USER/ADMIN常量，为密码属性添加序列化忽略注解。  

扩展UserService组件中集合对象数据，密码`123456`的一个编码。

```shell
$2a$10$XPz7Kp1kF8NU3vewqqPGn.feT7UPvhoZolvJ1JRi57s16XKMWz9OW
```

扩展LoginController组件，注入PasswordEncoder/JWTComponent组件。

- 扩展login()方法，重写登录验证，并签发包含userid/role的token

创建UserController组件，根路径`/api/user/`。  

- 创建处理`info`路径GET请求方法getInfo()方法，方法从requestattribute注入用户实际id

在interceptor包下，创建LoginInterceptor拦截器，注入JWTComponent组件，拦截除`/api/login`的全部请求，从请求header中获取/解析token数据，将uid置于requestattribute。  
在interceptor包下，创建Adminnterceptor拦截器，从requestattribute获取当前用户角色权限校验。  
创建实现WebMvcConfig接口的配置实现类WebMvcConfiguration，重写addInterceptors()方法，注册拦截器，设置拦截规则过滤。  

在test下http目录，创建test.http测试脚本。  

- 编写登录测试脚本请求，测试成功/失败响应，在响应中获取token数据
- 携带token信息，发送需登录请求

### 实验5 后端整合实验

**实验目的**  
理解后端微服务架构设计思想。  
理解微服务设计模式/层次/组件/安全等的作用与意义。  
掌握基于spring系列框架实现后端微服务应用的过程及方法。  

**实验内容**  
整合实验以理解微服务开发过程为主，可直接从示例/实验中复制代码使用。  
创建独立的backend-examples微服务，引入lombok/spring-data-jdbc/mysql/springmvc依赖，整理项目。  

**初始化**

基本配置。  
引入`通用组件`：主键算法；枚举异常业务码；自定义全局异常； 密码组件；jwt组件； 全局异常处理控制组件；vo类。  

**需求**  
基本：用户登录；初始化时添加管理员账号。  
管理员：添加用户；获取全部用户；重置指定账号密码。  
用户：更新自己密码。

**实现**

编写schema脚本  
User DO类，声明USER/ADMIN角色常量值。  
UserRepository持久层组件，实现基于账号获取用户。  
InitService业务层组件，基于容器监听器实现管理员账号初始化。  
UserService业务层组件，基于账号获取用户，基于uid获取用户，更新指定uid密码，获取全部用户，添加用户。  
LoginController控制层组件，登录。  
UserController控制层组件，  更新个人密码。  
AdminController控制层组件，添加用户，获取全部用户信息，重置指定账号密码。  
LoginInterceptor拦截器，AdminInterceptor拦截器。  
编写测试脚本。

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

### 实验 AOP切面实验

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