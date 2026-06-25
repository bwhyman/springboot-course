# Experiments

### 实验1 持久化实验

**实验目的**  
掌握基于 idea 的基本 springboot 工程项目的创建方法。  
理解 maven 项目结构，依赖配置声明管理等。  
掌握 DO 类与数据表的映射规则。  
掌握基本的属性映射规则。  
理解雪花算法主键生成策略与特点。  
掌握基于 mybatis-flex 框架的基本 CURD 操作。  

**实验内容**   
创建 springboot 项目，添加 mybatis-flex 框架相关依赖，整理项目资源。  
创建 springboot 项目配置文件，添加 数据源/logging 等基本配置。  
创建数据表生成脚本，编写user表结构。  
idea 添加连接远程数据库。  
执行脚本生成测试数据表。  
编写 user 表对应的 DO 类，按映射规则声明属性。  
编写雪花算法注入实现。  
编写操作 DO 类的 Mapper 组件。  
编写测试类，注入 Mapper 组件，执行增删改等持久化方法。

### 实验2 关联查询映射实验

**实验目的**  
理解数据表索引的重要作用。  
掌握编写 SQL 关联查询语句的方法。  
掌握关联查询的声明使用方法。  
掌握基于 QueryChain 的查询方法。  
掌握基于 Explain 的 SQL 语句分析与优化。

**实验内容**   
基于上一实验内容。  
在 schema 脚本添加 user/address 数据表，声明若干字段。  
在 address 包含 user 表主键并设置为索引；user 1:N address，address 1:1 user。  
项目声明数据源/启动执行 schema 脚本/logging 等基本配置。  
编写持久层组件 userMapper / AddressMapper 接口。  
编写测试用例，添加若干 user 记录，以及 user 对应的 address 记录。  

实现以下数据检索，且需先通过 explain 分析预执行的 SQL 语句，验证数据检索方式是否符合预期后再在组件实现。

基于 QUeryChain 实现
- 基于 userid ，查询全部 address 信息，通过 AddressMapper 组件查询即可。
- 基于 userid ，查询 address 信息以及 user 信息，扁平化封装。
- 基于 userid ，查询 user 信息，以及全部 addresses 信息，设计 DTO 类。
- 基于 userid ，查询姓名，以及拥有的 addresses 数量，设计 DTO 类。
- 尝试基于 userid ，将查询的 user/addresses 信息，封装为JSON。 

编写测试类测试。

### 实验3 SpringMVC 请求响应实验

**实验目的**  
理解并掌握 REST API 的设计及实现方法。  
理解并掌握 controller 组件的声明方法。  
理解并掌握 HTTP GET/POST/PATCH 请求的处理。  
理解并掌握 JSON 数据结构。   
理解并掌握基本基于 Jackson 的数据响应。   
理解并掌握基于 JSON 请求数据的封装。  
理解并掌握基于 idea 的 HTTP REST API 模拟请求脚本。  

**实验内容**  
创建 springboot 项目，添加 springmvc 框架相关依赖，整理项目资源。  
添加 log/jackson 忽略空属性等基本配置。  

在 exception 包下，自定义枚举类型通用异常业务码。  
在 vo 包下，创建 ResultVO 类，统一封装通用响应数据，包括：响应数据/响应空数据/异常业务数据等。  

在 entity 包下，创建 User 类，添加 id/name/account/password/createTime 等属性。  
在 service 包下，创建 UserService 组件，模拟一个包含若干 user 对象的集合

- 获取全部 users 集合对象业务方法，listUsers()
- 基于 account 获取 user 对象业务方法，getUserByAccount()

在 controller 包下，创建 LoginController 控制组件,`/api/`，添加基本组件声明，注入 UserService 组件。

- 创建处理`login`路径 POST 请求方法 login()，获取 user 对象，实现登录校验

在 controller 包下，创建 AdminController 控制组件,`/api/admin/`，添加基本组件声明

- 创建处理`users`路径 GET 请求方法 getUsers() 方法，获取全部用户信息

- 创建处理`users/{account}`路径 GET 请求方法 getUser() 方法，获取路径参数，调用业务组件查询

在 test 下创建 http 目录，创建 test.http 测试脚本，编写请求测试用例。

### 实验4 SpringMVC 拦截器与安全实验

**实验目的**  
理解拦截器的作用与意义。
掌握拦截器的声明配置方法。
掌握拦截器过滤指定请求的配置方法。
掌握拦截器的实现方法。
了解拦截器和使用场景。
掌握在拦截器中请求权限校验的方法。 

**实验内容**  
基于上一实验内容。  
添加 spring-security-crypto/java-jwt 依赖。  
扩展配置，自定义 token 密钥。  

在 exception 包下，自定义 unchecked 异常，包括处理通用业务异常，以及自定义异常信息。  
在 controller 包下，创建 ExceptionController 组件，统一处理异常信息。  
在 component 包下，创建 PasswordEncoderConfig 配置类，创建基于 BCryptPasswordEncoder 算法的 PasswordEncoder 组件。
在 component 包下，创建 JWTComponent 组件，注入配置中密钥，实现 token 的加密/解密。  

扩展 User 类，追加 role 属性，以及 USER/ADMIN 常量，为密码属性添加序列化忽略注解。  

扩展 UserService 组件中集合对象数据，密码`123456`的一个编码，添加 role 属性值。

```shell
$2a$10$XPz7Kp1kF8NU3vewqqPGn.feT7UPvhoZolvJ1JRi57s16XKMWz9OW
```

在 interceptor 包下，创建 LoginInterceptor 拦截器，注入 JWTComponent 组件，拦截除`/api/login`的全部请求，从请求 header 中获取/解析 token 数据，将 uid 置于 requestattribute 。  
在 interceptor 包下，创建 Adminnterceptor 拦截器，从 requestattribute 获取当前用户角色权限校验。  
在 component 包下，创建 WebMvcConfig 配置类实现 WebMvcConfig 接口，重写 addInterceptors() 方法，注册拦截器，设置拦截规则过滤。

扩展 LoginController 组件，注入 PasswordEncoder/JWTComponent 组件。

- 扩展 login() 方法，重写登录验证，并签发包含 userid/role 的 token 。

创建 UserController 组件，根路径`/api/user/`。  

- 创建处理`info`路径 GET 请求方法 getInfo() 方法，方法从 requestattribute 注入用户实际 id 。

在 test 下 http 目录，创建 test.http 测试脚本。  

- 编写登录测试脚本请求，测试成功/失败响应，在响应中获取 token 数据。
- 携带 token 信息，发送需登录请求。

### 实验5 后端整合实验

**实验目的**  
理解后端微服务架构设计思想。  
理解微服务设计模式/层次/组件/安全等的作用与意义。  
掌握基于 spring 系列框架实现后端微服务应用的过程及方法。  

**实验内容**  
整合实验以理解微服务开发过程为主，可直接从示例/实验中复制代码使用。  
创建独立的 backend-examples 微服务，引入 lombok/mybatis-flex/mysql/springmvc/crypto/jwt 等依赖，整理项目。  

**初始化**

基本配置。  
引入`通用组件`：主键算法；枚举异常业务码；自定义全局异常； 密码组件； jwt 组件； 全局异常处理控制组件；vo 类。  

**需求**  
基本：用户登录；初始化时添加管理员账号。  
管理员：添加用户；获取全部用户；重置指定账号密码。  
用户：更新自己密码。

**实现**

编写 schema 脚本。  
User DO 类，声明 USER/ADMIN 角色常量值。  
UserRepository 持久层组件，实现基于账号获取用户，单元测试。  
InitService 业务层组件，基于容器监听器实现管理员账号初始化。  
UserService 业务层组件，基于账号获取用户，基于 uid 获取用户，更新指定 uid 密码，获取全部用户，添加用户，单元测试。  
LoginController 控制层组件，登录。  
UserController 控制层组件，  更新个人密码。  
AdminController 控制层组件，添加用户，获取全部用户信息，重置指定账号密码。  
LoginInterceptor 拦截器， AdminInterceptor 拦截器。  
编写请求测试脚本。

### 实验6 Redis 限流实验

**实验目的**  
理解非关系型数据库的特点及适用场景。  
理解 redis 基本数据类型。
掌握基本 redis 命令。
掌握 lua 脚本的基本语法。  
掌握 redis 函数的声明方法。  
掌握 redisson 客户端的声明配置方法。  
掌握 redisson 基本接口类型的使用方法。

**实验内容**  
创建 springboot 项目，添加 redisson 等依赖。    
编写项目基本配置。  
编写 redis 数据源等基本 redisson 配置。  
idea database 视图添加 redis 数据源。打开 redis 控制台，修改控制台中数据库编号，测试 redis 基本命令。  
编写单元测试，测试基于 redisson 客户端组件实现 redis 操作。  
编写 lua 脚本函数，实现模拟 API 请求数据的统计及时效性检验。  
编写应用启动监听器，注册脚本库/函数到 redis 服务器。  
编写限流业务组件，注入 redisson 客户端调用 redis 函数实现。  
编写单元测试，测试限流组件的有效性。  

### 实验7 Redis stream 消息队列实验

**实验目的**  
理解消息中间件的特点及适用场景。  
理解基于 redis stream 实现消息队列的原理。  
理解 redis stream 中消费组/消费者/消息状态/消息确认等基本概念。  
理解 ULID 分布式主键算法。
掌握 spring-data-redis 框架的基本使用方法。  
掌握基于 redis 的消息监听器的声明配置方法。 
掌握创建消息容器/消息消费者等对象的声明配置方法。  
掌握消息发送确认的方法。  

**实验内容**  
基于上一实验内容。  
编写 ULID 分布式主键生成组件，单元测试。  
编写 DTO 类 Item，包含 ID，抢购商品数量，redis key 前缀。  
编写 DTO 类 Order，包含订单ID/商品ID/用户ID属性，以及 redis key 前缀/订单消息队列名称/消息组等名称常量。    

编写业务组件，实现将抢购商品添加至 redis 服务器业务逻辑，单元测试。  
编写 lua 脚本，实现针对指定商品的抢购逻辑函数。  
添加抢购业务逻辑，调用 redis 函数实现在高并发下商品抢购的一致性操作，在抢购成功后创建订单并存入 redis，单元测试。  

编写应用启动监听器，在 redis stream 注册消息消费组及消费组等，必须在应用启动时确保 redis stream 已经存在，否则发送/监听消息会异常。  
编写消息监听器，确认并消费订单处理消息。  
编写消息监听器容器配置，绑定指定消息队列。   
修改抢购业务逻辑，将订单 ID 以消息发送至消息队列处理。

编写单元测试，通过多线程模拟抢购业务操作测试执行。

### 实验 AOP 切面实验

**实验目的**
理解面向切面编程的作用与意义。  
理解动态代理技术。
掌握 spring AOP 框架的声明创建方法。  
掌握基本前置/后置/环绕等通知声明方法。  
掌握基本切面执行表达式的声明方法。  
掌握基于自定义注解/AOP 切面技术的权限控制。

**实验内容**   
创建 springboot 项目，添加 aop 依赖。  
声明日志等基本配置。  
创建自定义权限注解。  
编写业务组件，基于自定义权限注解模拟权限业务方法。  
创建基于自定义注解的权限切面类，声明基于注解的方法级/类级环绕拦截器，并模拟判断权限。
业务方法权限与请求用户权限不符则抛出自定义异常。  
编写测试类，测试业务组件的调用。

### 实验 Cache 缓存实验

**实验目的**  
理解业务层缓存的作用与意义。  
理解 spring-cache 缓存框架的实现原理。  
掌握 spring-cache 缓存框架的声明配置方法。  
掌握基本缓存注解的声明方法。  
掌握基本 SpEL 表达式使用方法。

**实验内容**  
创建 springboot 项目，添加 springmvc/cache 等实验依赖，声明启动缓存。  
创建测试 DTO 类；创建测试模拟持久层组件，实现基本获取更新操作。  
创建业务组件，获取全部用户，获取指定用户，更新指定用户业务逻辑。   
添加 spring-cache 缓存注解，对以上业务使用合适缓存策略。  