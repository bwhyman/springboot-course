# Experiments
### 实验5 持久化实验
**实验目的**  
掌握基于idea的基本springboot工程项目的创建方法  
理解maven项目结构，依赖配置声明管理  
掌握DO类与数据表的映射规则  
掌握基本的属性映射规则  
理解雪花算法主键生成策略与特点  
掌握基于spring-data-jdbc框架的基本CURD操作  

**实验内容**   
创建springboot项目，添加spring-data-jdbc框架相关依赖  
创建springboot项目配置文件，添加数据源/logging等相关配置  
创建数据表生成脚本，编写user表结构  
打开idea database视图，添加连接远程数据库  
执行脚本生成测试数据表  
编写user表对应的DO类，按映射规则声明属性  
编写操作DO类的Repository组件  
编写测试类，注入Repository组件，执行增删改等持久化方法  
测试事务  


### 实验6 关联查询映射实验
**实验目的**  
理解仅索引非外键关系的作用  
掌握编写关联查询语句的方法  
掌握关联查询的声明使用方法  
掌握基于RowMapper/ResultSetExtractor接口的结果集映射方法  

**实验内容**   
结合上一实验内容  
在schema脚本添加user/address，声明若干字段，并添加至数据库  
在address包含user表主键并设置为索引；user one-to-many address，address one-to-one user  
编写repository接口  
模拟1个user，对应2个address  
按需创建不同方式封装user/address中信息的DTO类  
实现以下查询

- 基于userid，查询全部address信息，通过address repository查询即可
- 基于addressid，查询address信息以及user信息，需DTO
- 基于userid，查询user信息，以及全部address信息，需DTO

编写测试类测试

### 实验7 SpringMVC实验
**实验目的**  
理解并掌握REST API的设计及实现方法  
理解并掌握controller组件的声明方法  
理解并掌握HTTP GET/POST/PATCH请求的处理  
理解并掌握JSON数据结构   
理解并掌握基本基于Jackson的数据响应   
理解并掌握基于json请求数据的封装  
理解并掌握基于idea的HTTP REST API模拟请求脚本  

**实验内容**  
创建一个支持SpringMVC的springboot项目  
添加log/jackson忽略空属性等基本配置  

需求0  
创建ResultVO类，统一处理响应数据  
在controller下，创建UserController控制组件，添加REST注解，声明/api根路径  
创建处理/index，请求方法，返回ResultVO对象，封装普通字符串即可  

需求+1  
在dto下，创建User类，添加用户名/密码，为密码添加序列化忽略注解，当序列化user对象时，将忽略密码属性  
```shell
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
```
在控制组件中模拟一个users集合，模拟封装若干user对象  
创建处理/users，get请求方法，封装users集合   
创建处理/users/{uid}，get请求方法，获取请求地址user ID参数，基于参数从users集合中获取对象，封装到返回。注意Map.of()不能添加null，因此需处理如果集合中没有指定ID对象的实现  
创建处理/users，post请求方法，将请求数据封装到User对象，作为参数注入方法，将对象添加到users集合  

在test下，创建http文件夹，创建user.http测试脚本测试请求  

### 实验8 SpringMVC拦截器实验
**实验目的**  
理解拦截器的作用与意义
掌握拦截器的声明配置方法
掌握拦截器过滤指定请求的配置方法
掌握拦截器的实现方法
了解拦截器回调方法的区别和使用场景
掌握在拦截器中请求权限验证 

**实验内容**  
创建一个支持SpringMVC的springboot项目

需求0  
添加Spring Security框架中的加密解密依赖。注意：不是在构建工程时添加Spring Security整体框架，仅需其中的crypto依赖
在application中添加log/jackson忽略空属性配置，以及自定义加密密钥与盐值  

在component下，创建整合Jackson的加密/解密组件EncryptorComponent，注入密钥与盐值，实现对给定Map对象的加密/解密，对无法解密异常，转抛为自定义异常  
创建SecurityConfiguration配置类，创建基于BCryptPasswordEncoder算法的PasswordEncoder对象，注入spring容器  
在dto下创建User类，添加userName/password属性，getter/setter注解等；添加password的序列化忽略注解  
在controller下，创建LoginController组件，注入密码编码组件，注入加密/解密组件。组件内创建一个Map，模拟基于用户名保存用户对象  
创建处理/register post请求方法，将注册用户密码编码，将user对象保存在Map对象中(模拟数据库)  
创建处理/login post请求方法，判断登录用户是否存在，用户密码是否正确；并将用户名加密保存在响应header中  
在interceptor下，创建LoginInterceptor拦截器，从请求header中获取token数据，解密并将解密出的用户名置于requestattribute中  

创建实现WebMvcConfigurer接口的配置实现类WebMvcConfiguration，重写addInterceptors()方法，注册拦截器，设置拦截规则过滤  

在LoginController组件中，创建处理/index get请求，在方法中注入requestattribute中的用户名，并将用户名返回

测试  
在test下，创建http目录，创建login.http测试脚本  
编写注册测试脚本  
编写登录测试脚本测试，在响应中获取token数据  
编写/index请求测试脚本，在header中携带token发起请求  
