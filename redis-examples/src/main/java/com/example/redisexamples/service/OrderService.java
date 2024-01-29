package com.example.redisexamples.service;

import com.example.redisexamples.component.ULID;
import com.example.redisexamples.dox.Item;
import com.example.redisexamples.dox.Order;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.redisson.api.stream.StreamAddArgs;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {
    private final RedissonClient redissonClient;
    private final ULID ulid;

    // 将指定商品批量加入redis抢购数据库
    @Transactional
    public void addItems(List<Item> items) {
        RBatch batch = redissonClient.createBatch();
        for (Item item : items) {
            batch.getMap(Item.PRE_KEY + item.getId(), StringCodec.INSTANCE)
                    .putIfAbsentAsync("total", item.getTotal());
        }
        batch.execute();
    }

    // 传入抢购的商品，抢购用户
    @Transactional
    public long rushBuy(Item item, String userId) {
        var key = Item.PRE_KEY + item.getId();
        long quantity = redissonClient
                .getFunction()
                .call(FunctionMode.WRITE, "rushBuy", FunctionResult.LONG, List.of(key));
        if (quantity == -1) {
            log.debug("抢光啦");
            return 0;
        }
        // 抢购成功，则基于用户ID执行扣款等操作
        log.debug("{}，抢购成功", userId);
        // 创建订单
        Order order = Order.builder().id(ulid.nextULID()).itemId(item.getId()).userId(userId).build();
        // 默认基于Kryo5Codec序列化对象，非json可读。
        RBucket<Order> bucket = redissonClient.getBucket(Order.PRE_KEY + order.getId());
        // 持久化订单
        bucket.set(order);
        // 发送到订单处理消息队列，异步延迟处理订单，减轻服务器压力
        pushOrderQueue(order);
        return quantity;
    }

    private void pushOrderQueue(Order order) {
        RStream<String, String> stream = redissonClient.getStream(Order.STREAM_KEY, StringCodec.INSTANCE);
        // 仅需在消息体添加订单ID
        stream.add(StreamAddArgs.entry("orderid", order.getId()));
    }
}
