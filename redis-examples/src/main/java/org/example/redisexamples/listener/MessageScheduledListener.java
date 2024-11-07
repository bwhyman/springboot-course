package org.example.redisexamples.listener;

import org.example.redisexamples.dox.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.StreamMessageId;
import org.redisson.api.stream.StreamReadGroupArgs;
import org.redisson.client.codec.StringCodec;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

//@Component
//@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class MessageScheduledListener {
    private final RedissonClient client;

    @Scheduled(fixedDelay = 2000)
    public void onMessage() {
        // 初始化消费组
        RStream<String, String> stream = client.getStream(Order.STREAM_KEY, StringCodec.INSTANCE);
        Map<StreamMessageId, Map<String, String>> sm = stream.readGroup(Order.GROUP_NAME, Order.GROUP_CONSUMER, StreamReadGroupArgs.neverDelivered());
        sm.forEach((mid, v) -> {
            log.debug("MessageId: {} / orderid: {}", mid, v);
            // 返回确认消费者消费了消息
            stream.ack(Order.GROUP_NAME, mid);
            // 移除已消费消息。为测试没有移除
            // stream.remove(mid);
        });
    }
}
