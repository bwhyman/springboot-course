package com.example.redisexamples.listener;

import com.example.redisexamples.dox.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.stream.StreamCreateGroupArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;
import java.util.concurrent.Executors;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MessageListenerContainerConfiguration {

    private final RedisConnectionFactory redisConnectionFactory;
    private final RedissonClient client;
    private final OrderMessageListener listener;

    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, String>> register() {
        // 初始化消费组
        RStream<Object, Object> stream = client.getStream(Order.STREAM_KEY);
        if (!stream.isExists()) {
            // 如果键不存在，创建group的同时创建键
            stream.createGroup(StreamCreateGroupArgs.name(Order.GROUP_NAME).makeStream());
        }
        // 注册监听器
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        // 默认使用执行短操作，反复创建销毁的伪线程池
                        .executor(Executors.newSingleThreadExecutor())
                        // Stream 中没有消息时阻塞多长时间。需要比 `spring.redis.timeout` 的时间小
                        .pollTimeout(Duration.ofMillis(100))
                        // 转换消息体为具体类型
                        .targetType(String.class).build();
        StreamMessageListenerContainer<String, ObjectRecord<String, String>> container = StreamMessageListenerContainer.create(redisConnectionFactory, options);

        // 可使用receiveAutoAck()方法接收后自动返回确认。但不会自动移除，可在onMessage()消费后收到确认/移除
        // 消费组名称，消费者名称
        container.receive(Consumer.from(Order.GROUP_NAME, Order.GROUP_CONSUMER), StreamOffset.create(Order.STREAM_KEY, ReadOffset.lastConsumed()), listener);

        container.start();
        return container;
    }
}
