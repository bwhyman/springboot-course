package org.example.redisexamples.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.redisson.client.codec.IntegerCodec;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpireService {
    private final RedissonClient client;
    private final String pre_key = "expires:agentids:";

    // expireSec秒内，执行超过count次返回flase
    // lua函数参数仅支持传入java string/number类型
    public boolean requestCheckByIncrex(String uid, int count, int expireSec) {
        var key = pre_key + uid;
        // 获取 redis 函数操作对象
        // 传入的参数会按默认codec序列化。因此显式声明按int传入
        return client.getFunction(IntegerCodec.INSTANCE)
                // 调用模式，调用注册函数名称，返回类型，可变长度参数...
                .call(FunctionMode.WRITE,
                        "expireByIncrex_0",
                        FunctionResult.BOOLEAN,
                        List.of(key),
                        count, expireSec);
    }

    public boolean requestCheckByTokenBucket(String uid, int count, int expireSec) {
        var key = pre_key + uid;
        RRateLimiter limiter = client.getRateLimiter(key);
        // 执行时记录存在则自动忽略设置
        // 不存在概率更高，因此不判断而直接设置可减少一次网络请求
        limiter.trySetRate(RateType.OVERALL,
                count,
                Duration.ofSeconds(expireSec),
                Duration.ofMinutes(1));
        // 拿1个令牌，没拿到2s后再次尝试
        return limiter.tryAcquire(1, Duration.ofSeconds(2));
    }

    /*public boolean requestCheck(String key, int count, int expireSec) {
        // 获取redis 函数操作对象
        // 传入的参数会按默认codec序列化。因此显式声明按int传入
        RFunction rf = client.getFunction(IntegerCodec.INSTANCE);
        // 参数：调用模式；调用的注册函数名称；返回类型；keys；args...
        return rf.call(
                FunctionMode.WRITE,
                "expireAPICount_0",
                FunctionResult.BOOLEAN,
                List.of(key),
                expireSec, count);
    }*/
}
