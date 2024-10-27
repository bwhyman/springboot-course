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

    @EventListener(classes = ApplicationReadyEvent.class)
    public void loadRedisScript() throws IOException {
        // 读取脚本内容
        String script = resource.getContentAsString(Charset.defaultCharset());
        RFunction rf = redissonClient.getFunction();
        // 不是脚本的名称，是库的名称，没有扩展名
        var name = resource.getFilename();
        assert name != null;
        // replace，用于重启时避免再次加载冲突
        rf.loadAndReplace(name.substring(0, name.lastIndexOf(".")), script);
    }
}
