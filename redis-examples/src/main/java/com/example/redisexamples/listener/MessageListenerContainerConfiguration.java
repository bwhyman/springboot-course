package com.example.redisexamples.listener;

import com.example.redisexamples.dox.Order;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MessageListenerContainerConfiguration {

    private final RedisConnectionFactory redisConnectionFactory;
    private final OrderMessageListener listener;
    private final ExecutorService executor =  Executors.newSingleThreadExecutor();

    @PreDestroy
    public void destroyExecutor() throws InterruptedException {
        executor.shutdown();
        if (executor.awaitTermination(2, TimeUnit.SECONDS)) {
            log.debug("executor.awaitTermination");
        }
    }
    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, String>> register() {
        // 注册监听器
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        // 自定义单线程线程池。默认使用为执行短操作反复创建销毁的伪线程池
                        .executor(executor)
                        // Stream 读取消息时的阻塞时间
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
