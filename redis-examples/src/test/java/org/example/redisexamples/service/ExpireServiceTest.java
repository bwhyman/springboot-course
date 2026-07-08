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
    // 基于redis:8.8新指令实现
    @Test
    void requestCheckByIncrex() throws InterruptedException {
        var uid = "6561";
        var count = 2;
        var expireSec = 5;
        for (int i = 0; i < 11; i++) {
            boolean success = expireService.requestCheckByIncrex(uid, count, expireSec);
            log.debug("{}", success);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    void requestCheckByTokenBucket() throws InterruptedException {
        var uid = "6562";
        var count = 2;
        var expireSec = 5;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 11; i++) {
            boolean success = expireService.requestCheckByTokenBucket(uid, count, expireSec);
            long temp = (System.currentTimeMillis() - start) / 1000;
            log.debug("{} sec - {}", temp, success);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    void requestCheckByIncrex2() throws InterruptedException {
        var uid = "6561";
        var count = 10;
        var expireSec = 10;
        for (int i = 0; i < 20; i++) {
            boolean success = expireService.requestCheckByIncrex(uid, count, expireSec);
            log.debug("{}-{}", System.currentTimeMillis(), success);
            //TimeUnit.SECONDS.sleep(1);
        }
    }
    @Test
    void requestCheckByTokenBucket2() throws InterruptedException {
        var uid = "6562";
        var count = 2;
        var expireSec = 5;
        for (int i = 0; i < 20; i++) {
            Thread.startVirtualThread(() -> {
                boolean success = expireService.requestCheckByTokenBucket(uid, count, expireSec);
                log.debug("{}-{}", System.currentTimeMillis(), success);

            });
        }

        TimeUnit.SECONDS.sleep(10);
    }

    /*@Test
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
    }*/

}
