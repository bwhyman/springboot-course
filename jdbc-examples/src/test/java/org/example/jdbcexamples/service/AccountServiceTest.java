package org.example.jdbcexamples.service;

import org.example.jdbcexamples.dox.Account;
import org.example.jdbcexamples.dox.AccountPess;
import org.example.jdbcexamples.repository.AccountPessRepository;
import org.example.jdbcexamples.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
@Slf4j
public class AccountServiceTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountPessRepository accountPessRepository;
    @Autowired
    private AccountService accountService;

    @Test
    public void addAccountTest() {
        Account account = Account.builder()
                .balance(1000F).name("BO").build();
        accountRepository.save(account);
    }

    @Test
    public void update() throws InterruptedException {
        int count = 2;
        CountDownLatch latch = new CountDownLatch(count);
        // 使用逻辑线程
        for (int i = 0; i < count; i++) {
            Thread.ofPlatform()
                    .start(() -> {
                        try {
                            accountService.update("1284555615357681664", 200);
                        } finally {
                            latch.countDown();
                        }
                    });
        }
        latch.await();
    }

    //
    @Test
    void saveAccountPess() {
        var accountPess = AccountPess.builder()
                .balance(1000F)
                .name("BO")
                .build();
        accountPessRepository.save(accountPess);
    }

    // 测试通过悲观锁实现
    @Test
    public void updatePess() throws InterruptedException {
        int count = 2;
        CountDownLatch latch = new CountDownLatch(count);
        // 使用VT
        for (int i = 0; i < count; i++) {
            Thread.startVirtualThread(() -> {
                try {
                    accountService.updatePess("1284554842091601920", 200);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
    }

    // 测试通过SQL语句实现更新
    @Test
    void updateBalance() throws InterruptedException {
        int count = 2;
        CountDownLatch latch = new CountDownLatch(count);
        // 使用VT
        for (int i = 0; i < count; i++) {
            Thread.startVirtualThread(() -> {
                try {
                    accountService.updateBalance("1284554842091601920", 200);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
    }
}
