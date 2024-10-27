package org.example.redisexamples.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
public class ExpireServiceTest {
    @Autowired
    private ExpireService expireService;

    @Test
    void expireTest() throws InterruptedException {
        // 模拟API请求限流。
        var key = "expires:agentids:6561";
        var count = 2;
        var expireSec = 5;
        for (int i = 0; i < 11; i++) {
            boolean success = expireService.requestCheck(key, count, expireSec);
            log.debug("{}", success);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
