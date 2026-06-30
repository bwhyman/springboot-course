# Frameworks for Web Application - Springboot Course

### Overview

China, Northeast Forestry University, Software Engineering, Frameworks for Web Application.

Web系统框架，是东北林业大学软件工程专业第5学期的一门专业选修课。课程包含32+16学时。  
主讲教师：王波老师。  

课程基于 Springboot/spring Cloud 框架的后端微服务架构、设计思想与实现技术。  
课程的具体技术内容包括：
- 基于mybatis-flex 的半自动面向对象持久化技术
- 基于 spring-data-jdbc 的半自动面向对象持久化技术
- 基于 PostgreSQL 的关系型/非关系型混合开发技术
- 基于 Spring 框架的业务逻辑层技术
- 基于 springmvc 框架的控制层技术
- 基于 springdoc-openapi 框架的 API 接口文档生成技术
- 基于 spring-security 框架的安全服务技术
- 基于 JWT/自定义加密数据的分布式单点登录鉴权技术
- 基于 JSR349 规范的数据校验技术
- 基于 Timer 的定时服务技术
- 基于 AOP 的切面技术
- 基于自定义注解/反射/拦截器/AOP 的细粒度权限控制与业务逻辑功能扩展
- 基于自定义异常的全局异常处理
- 基于 Junit5/Spring-Testing/Mock 框架的测试技术
- 基于 spring-cache 的缓存技术
- 基于 Redis 的缓存/限流/消息队列等技术
- 基于 webflux/r2dbc 的异步非阻塞持久化技术
- [基于spring-cloud的微服务网关/服务注册发现技术](https://github.com/bwhyman/uber-project-examples)
- 基于 spring-ai 的AI应用程序开发技术

### Development Environments

开发环境/框架及版本：

- IntelliJ IDEA Ultimate
- OpenJDK ^25
- Springboot ^4.1
- Git ^2.5
- PostgreSQL ^18.4
- Redis ^8.6

### Updates

#### 2026-06-26

数据库正式从 MySQL 切换到 PostgreSQL。  
面对 AI 向量数据/地理空间数据/JSON数据/丰富的索引类型以及N年前就支持各种窗口函数的 PostgreSQL， MySQL 能塞入不能计算相似度的向量数据，能建索引不能关联查询的JSON等等， 太让人失望了。  

引入 mybatis-flex 持久化框架。类似 JOOQ 的 QueryChain，以及各种开箱即用的功能更符合国内开发环境。  

springboot:4+，更新了部分starter依赖
- spring-boot-starter-aop, spring-boot-starter-aspectj
- spring-boot-starter-web, spring-boot-starter-webmvc

### Examples

课程代码由单工程多模块组成：

- /mybatisflex-examples
- /jdbc-examples
- /spring-examples
- /springmvc-examples
- /cache-examples
- /redis-examples
- /webflux-r2dbc-examples
- /consul-examples
- /backend-examples，为前端项目提供模拟数据互交
- [/spring-ai-example](https://github.com/bwhyman/spring-ai-examples)
- /springsecurity-examples

### Online Tutorials

- [在线课程](https://mooc1-1.chaoxing.com/course/208931964.html)

### Backend Integration

[后端微服务框架整合](https://mooc1.chaoxing.com/nodedetailcontroller/visitnodedetail?courseId=208931964&knowledgeId=298571472)

[前后端联调](https://mooc1.chaoxing.com/nodedetailcontroller/visitnodedetail?knowledgeId=300177471&courseId=208931964)

### Microservices

整合基于springboot的微服务/基于spring-cloud-gateway的异步非阻塞网关微服务/
基于spring-cloud-alibaba-nacos的微服务注册与发现服务/华为云服务器/Docker容器的，开发测试与生产环境下部署的流程演示。

- https://github.com/bwhyman/uber-project-examples
- [视频讲解](https://mooc1-1.chaoxing.com/nodedetailcontroller/visitnodedetail?courseId=208931964&knowledgeId=394488338)

### Continuous Deployment

基于GitHub Actions工作流/GitHub Packages/华为云服务器的，持续集成/持续交付/持续部署示例

- [视频讲解](https://mooc1-1.chaoxing.com/nodedetailcontroller/visitnodedetail?courseId=208931964&knowledgeId=326897803)

### Related Courses

- https://github.com/bwhyman/java-course
- https://github.com/bwhyman/web-course
- https://github.com/bwhyman/springboot-course
- https://github.com/bwhyman/vite-vue3-examples
- https://github.com/bwhyman/flutter_examples
