package com.example.redisexamples;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
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

    @Test
    void setStringTest() {
        RBucket<String> bucket = redissonClient.getBucket("name:" + "1", StringCodec.INSTANCE);
        bucket.set("BO");
    }

    @Test
    void setHashTest() {
        RMap<String, Object> map = redissonClient.getMap("bike:2", StringCodec.INSTANCE);
        map.put("brand", "守夜人");
        map.put("type", "Enduro bikes");
        map.put("model", "Deimos");
        map.put("price", 4972);
    }

    @Test
    void getOrderTest() {
        RBucket<Order> bucket = redissonClient.getBucket("orders:01HN8NETKWS5AYFME53H0559SV");
        log.debug("{}", bucket.get());

    }
}
