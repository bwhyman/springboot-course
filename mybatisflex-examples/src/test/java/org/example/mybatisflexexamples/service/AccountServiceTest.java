package org.example.mybatisflexexamples.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @Test
    void update() throws InterruptedException {
        int count = 2;
        var latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            Thread.startVirtualThread(() -> {
                try {
                    accountService.update(30206457927744L, 100);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
    }

    @Test
    void updatePess() throws InterruptedException {
        int count = 2;
        var latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            Thread.startVirtualThread(() -> {
                try {
                    accountService.updatePess(30206457927745L, 100);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
    }
}