package org.example.redisexamples.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RFunction;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class LoadRedisScriptListener {

    private final RedissonClient redissonClient;

    // 直接从编译后的classpath路径下读取文件
    @Value("mylib.lua")
    private Resource resource;

    @EventListener(ApplicationReadyEvent.class)
    public void loadRedisScript() throws IOException {
        // 读取脚本内容
        String script = resource.getContentAsString(Charset.defaultCharset());
        RFunction rf = redissonClient.getFunction();
        // 由于使用的是同一个redis数据库实例，如果库/函数名称相同，加载时会覆盖
        // 因此，以数据库编号为后缀区别
        // replace，用于重启微服务时覆盖避免冲突
        rf.loadAndReplace("mylib_" + 0, script);
    }
}
