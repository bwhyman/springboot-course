package com.example.redisexamples.listener;

import com.example.redisexamples.dox.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.stream.StreamCreateGroupArgs;
import org.redisson.client.codec.StringCodec;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class CreateRedisStreamListener {

    private final RedissonClient redissonClient;

    // 应用启动时在redis创建order stream
    // 确保使用时指定steam一定存在
    @EventListener(classes = ApplicationReadyEvent.class)
    public void createOrderStream() {
        // 消息ID类型；消息体类型
        RStream<String, String> stream = redissonClient.getStream(Order.STREAM_KEY, StringCodec.INSTANCE);
        if (!stream.isExists()) {
            // 如果键不存在，创建stream键/消费组/消费组
            stream.createGroup(StreamCreateGroupArgs.name(Order.GROUP_NAME).makeStream());
            stream.createConsumer(Order.GROUP_NAME, Order.GROUP_CONSUMER);
        }
    }
}
