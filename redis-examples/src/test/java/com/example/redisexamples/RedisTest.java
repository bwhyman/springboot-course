package com.example.redisexamples;

import com.example.redisexamples.component.ULID;
import com.example.redisexamples.dox.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class RedisTest {
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private ULID ulid;

    @Test
    void setStringTest() {
        // 使用`类型:ID`命名键
        RBucket<String> strSet = redissonClient.getBucket("name:" + "1", StringCodec.INSTANCE);
        log.debug("键为`name:1`的数据是否存在：{}", strSet.isExists());
        // 不存在创建，存在则覆盖
        strSet.set("BO");
        log.debug("键为`name:1`的数据是否存在：{}", strSet.isExists());
        RBucket<String> strGet = redissonClient.getBucket("name:" + "1", StringCodec.INSTANCE);
        log.debug(strGet.get());
    }

    @Test
    void setHashTest() {
        // 映射指定键对应的redis hash为Java Map类型
        // 因为hash字段对应的值可能时字符串与整型，因此声明为Object
        RMap<String, Object> map = redissonClient.getMap("bike:2", StringCodec.INSTANCE);
        map.put("brand", "守夜人");
        map.put("type", "Enduro bikes");
        map.put("model", "Deimos");
        map.put("price", 4972);
    }

    @Test
    void setOrderTest() {
        RMap<String, Object> map = redissonClient.getMap("bike:2", StringCodec.INSTANCE);
        if (map.isExists()) {
            map.forEach((k,v) -> log.debug("{} / {}", k, v));
        }
    }
    @Test
    void getOrderTest() {
        // 模拟order ID
        var orderId = ulid.nextULID();
        // 创建Order类型+ID的redis键
        var key = Order.PRE_KEY + orderId;
        // Kryo5序列化
        RBucket<Order> bucket = redissonClient.getBucket(key);
        bucket.set(Order.builder().id(orderId).userId("1").itemId("1").build());
        // 映射的新对象
        RBucket<Order> bucketN = redissonClient.getBucket(key);
        // 反序列化为Java对象。因具体化了泛型类型，支持类型检测
        Order order = bucketN.get();
        log.debug("{}", order);
    }
}
