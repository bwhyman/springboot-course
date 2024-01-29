package com.example.redisexamples.listener;

import com.example.redisexamples.dox.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.convertor.StreamIdConvertor;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderMessageListener implements StreamListener<String, ObjectRecord<String, String>> {
    private final RedissonClient client;
    private final StreamIdConvertor convertor = new StreamIdConvertor();
    @Override
    public void onMessage(ObjectRecord<String, String> message) {
        log.debug("onMessage:orderid: {}", message.getValue());
        RStream<String, String> stream = client.getStream(Order.STREAM_KEY);
        // 将RecordId转为StreamMessageId
        var smid = convertor.convert(message.getId().getValue());
        // 返回确认消费者消费了消息
        stream.ack(Order.GROUP_NAME, smid);
        // 移除已消费消息
        // stream.remove(smid);

    }
}
