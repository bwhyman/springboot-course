package com.example.redisexamples.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.FunctionMode;
import org.redisson.api.FunctionResult;
import org.redisson.api.RFunction;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.IntegerCodec;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpireService {
    private final RedissonClient client;

    // expireSec秒内，执行超过count次返回flase
    // lua函数参数仅支持传入java string/number类型
    public boolean requestCheck(String key, int count, int expireSec) {
        // 获取redis 函数操作对象
        // 传入的参数会按默认codec序列化。因此显式声明按int传入
        RFunction rf = client.getFunction(IntegerCodec.INSTANCE);
        // 参数：调用模式；调用的注册函数名称；返回类型；keys；args...
        return rf.call(
                FunctionMode.WRITE,
                "expireAPICount",
                FunctionResult.BOOLEAN,
                List.of(key),
                expireSec, count);
    }
}
