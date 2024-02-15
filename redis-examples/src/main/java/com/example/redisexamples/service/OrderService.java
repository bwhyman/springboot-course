package com.example.redisexamples.service;

import com.example.redisexamples.component.ULID;
import com.example.redisexamples.dox.Item;
import com.example.redisexamples.dox.Order;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.redisson.api.stream.StreamAddArgs;
import org.redisson.api.stream.StreamCreateGroupArgs;
import org.redisson.client.codec.IntegerCodec;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {
    private final RedissonClient redissonClient;
    private final ULID ulid;

    // 将指定商品批量加入redis抢购数据库
    public void addItems(List<Item> items) {
        // 获取操作管道的批处理对象
        RBatch batch = redissonClient.createBatch();
        for (Item item : items) {
            // 获取操作redis hash对象
            // 字段值需要计算，以整数存储
            RMapAsync<String, Integer> map =
                    batch.getMap(Item.PRE_KEY + item.getId(), IntegerCodec.INSTANCE);
            map.putIfAbsentAsync("total", item.getTotal());
        }
        batch.execute();
    }

    // 传入抢购的商品，抢购用户
    public long rushBuy(Item item, String userId) {
        var key = Item.PRE_KEY + item.getId();
        // 调用redis抢购函数
        long quantity = redissonClient
                .getFunction()
                .call(FunctionMode.WRITE, "rushBuy", FunctionResult.LONG, List.of(key));
        if (quantity == -1) {
            log.debug("抢光啦");
            return quantity;
        }
        // 抢购成功，则基于用户ID执行扣款等操作
        log.debug("{}，抢购成功", userId);
        // 创建订单
        Order order = Order.builder()
                .id(ulid.nextULID())
                .itemId(item.getId())
                .userId(userId)
                .build();
        // 默认基于Kryo5Codec序列化对象，非json可读。
        RBucket<Order> bucket = redissonClient.getBucket(Order.PRE_KEY + order.getId());
        // 持久化订单
        bucket.set(order);
        // 发送到订单处理消息队列，异步延迟处理订单，减轻服务器压力
        pushOrderQueue(order);
        return quantity;
    }

    private void pushOrderQueue(Order order) {
        // 消息ID类型；消息体类型
        RStream<String, String> stream = redissonClient.getStream(Order.STREAM_KEY, StringCodec.INSTANCE);
        if (!stream.isExists()) {
            // 如果键不存在，创建stream键/消费组/消费组。确保没有启动监听器也能创建stream
            stream.createGroup(StreamCreateGroupArgs.name(Order.GROUP_NAME).makeStream());
            stream.createConsumer(Order.GROUP_NAME, Order.GROUP_CONSUMER);
        }
        // 仅需在消息体添加订单ID
        stream.add(StreamAddArgs.entry("orderid", order.getId()));

    }
}
