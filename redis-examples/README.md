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
keys，需要操作的键列表，因此即使不用调用是也应传入空键。  
args，函数所需参数列表。  
LoadRedisScriptListener，基于springboot启动事件监听回调，在服务启动时自动加载函数脚本至redis服务器。

### Expire Keys
模拟第三方平台接口调用限流，每账号在30s内允许调用3次。  
简单的递增计数+数据时效性，通过Redis String实现即可
- 收到请求后从请求中获取请求账号(校验合法性)
- 调用redis函数，检索限流数据中是否包含指定账号记录
- 不存在，说明时间段内没有调用请求。以账号为键创建String类型数据记录，初始化计数1，并添加过期时间，返回允许
- 存在，则获取调用计数比较限制次数，超过返回禁止；未超过，递增计数+1，返回允许

mylib.lua脚本，expireAPICount函数。  
ExpireTest，限流测试。指定时间段内的请求次数限制。

### Redis Streams & Message Queues
OrderMessageListener，订单消息监听器，手动确认/移除消息。  
MessageListenerContainerConfiguration，注册订单消息监听器，并创建消费组/消费者。   
不存在没有`消费组`的stream，因此测试时可整体移除stream，但不能仅移除stream下的`消费组`。  

Container 默认使用执行短操作，反复创建销毁的伪线程池。  
也可通过spring Scheduled定时任务轮询redis消息队列。  

MKSTREAM，redis stream参数。创建group时stream不存在则自动创建，对应makeStream()方法。

### Limited-Time Offers
秒杀抢购3件套。限流/内存单线程数据库/消息队列。

需求：模拟商品抢购。  
高并发下需确保抢购商品数量的一致性(SQL数据库的悲观锁)，通过redis单线程执行hash hincrby指令特性，实现商品数量的原子性改变。  
通过将订单处理数据发送至消息队列异步处理，减轻抢购服务器压力。  
结合Redis函数/lua脚本函数/Hash数据类型/Stream消息队列实现
以商品ID为键，以Redis Hash存储商品数量
抢购时，基于商品键获取数量并判断是否售空；
- 已售空，返回-1。如果返回0，则无法区分是已售空，还是本次抢购后数量为0(也可以将售空商品移除，通过键的存在判断售空)。
- 未售空，数量减1，并返回剩余商品数量
- 抢购成功后，创建存储订单数据，并将订单以消息发送至订单处理消息队列
- 订单处理消息监听器，接收消息并处理订单

mylib.lua 脚本，rushBuy 函数。  
OrderService.addItems()，向redis添加hash类型抢购商品数据。   
OrderService.rushBuy()，抢购方法，调用脚本抢购函数，创建订单记录。  
OrderService.pushOrderQueue()，发送订单处理消息方法。  
OrderServiceTest，测试类。  

### Spring Cache Actuator
可通过spring-boot-starter-actuator监控缓存命中率等数据

### Others
基于redisson创建stream键与消费组
```shell
RStream<String, String> stream = client.getStream(Order.STREAM_KEY);
        if (!stream.isExists()) {
            // 如果stream键不存在，创建group的同时创建stream
            stream.createGroup(StreamCreateGroupArgs.name(Order.GROUP_NAME).makeStream());
        }
```

```shell
if (Boolean.FALSE.equals(template.hasKey(Order.STREAM_KEY))) {
            redisConnectionFactory.getConnection().streamCommands()
                    .xGroupCreate(Order.STREAM_KEY.getBytes(),
                            Order.GROUP_NAME,
                            ReadOffset.from("0-0"),
                            true);
        }
```

```shell
client.getStream(Order.GROUP_NAME).ack(Order.GROUP_NAME, smid);
```

```shell
template.opsForStream().acknowledge(Order.GROUP_NAME, message);
template.opsForStream().delete(Order.STREAM_KEY, message.getId());
```