package com.example.jdbcexamples.service;

import com.example.jdbcexamples.dox.Account;
import com.example.jdbcexamples.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
public class AccountServiceTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    @Test
    public void addAccountTest() {
        Account account = Account.builder()
                .balance(1000F).name("BO").build();
        accountRepository.save(account);
    }

    @Test
    public void expireTest() throws InterruptedException {
        int count = 2;
        CountDownLatch latch = new CountDownLatch(count);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < count; i++) {
            service.execute(() -> {
                try {
                    Thread.sleep(1000);
                    accountService.update("1057672204410146816", 600);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        service.shutdown();
    }
}
