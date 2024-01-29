# redis-examples

### Introductions
Redis服务器: redis-stack-server。  
支持Redis7/RediSearch/RedisJSON等。

Java客户端：redisson-spring-boot-starter。   
支持redis-stack扩展的实现；  
支持原子变量/分布式锁/队列/集合消息等；  
支持可扩展的Java数据结构；  
基于非阻塞Netty框架，可以便捷的切换为响应式异步非阻塞编程模式；  

### ULID
使用分布式ULID主键生成策略，由时间/随机数/base32编码组成的26位字符串。

### Redis Functions
redis要求函数应仅基于传入的Redis keys操作记录。   
keys，需要操作的键，因此即使无需也要传入。  
args，函数所需参数。  
基于springboot启动事件监听回调，在服务启动时自动加载函数脚本至redis服务器。

### Limited-Time Offers
秒杀抢购3件套。限流/内存单线程数据库/消息队列。  
并发请求下，redis通过单线程执行mylib.lua脚本函数中的hash hincrby命令，实现商品数量的原子性改变。  

OrderService.addItems()，向redis添加hash类型抢购商品数据。   
OrderService.rushBuy()，抢购方法，调用脚本抢购函数，创建订单记录。  
OrderService.pushOrderQueue()，发送订单处理消息方法。  

### Redis Streams & Message Queues
OrderStreamListener，订单消息监听器，手动确认/移除消息。  
RedisStreamConfiguration，注册订单消息监听器，并创建消费组/消费者。   
不存在没有`消费组`的stream，因此测试时可整体移除stream，但不能仅移除stream下的`消费组`。

MKSTREAM，redis stream参数。创建group时stream不存在则自动创建，对应makeStream()方法。  

### Spring Cache Actuator
可通过spring-boot-starter-actuator监控缓存命中率等数据