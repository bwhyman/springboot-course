package com.example.redisexamples;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.FunctionMode;
import org.redisson.api.FunctionResult;
import org.redisson.api.RFunction;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.IntegerCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
public class ExpireTest {
    @Autowired
    private RedissonClient client;

    @Test
    void expireTest() throws InterruptedException {
        // 模拟API请求限流。
        var key = "expires:agentids:6561";
        // expireSec秒内，执行超过count次返回flase
        // lua函数参数仅支持传入java string/int类型
        var count = 3;
        var expireSec = 30;
        for (int i = 0; i < count + 2; i++) {
            // 获取redis 函数操作对象
            // 传入的参数会按默认codec序列化
            // 但此处参数需要运算而非存储，因此显式声明按int传入
            RFunction rf = client.getFunction(IntegerCodec.INSTANCE);
            // 参数：调用模式；调用的注册函数名称；返回类型；keys；args...
            boolean success = rf.call(
                    FunctionMode.WRITE,
                    "expireAPICount",
                    FunctionResult.BOOLEAN,
                    List.of(key),
                    expireSec, count);
            log.debug("{}", success);
            //
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
