package org.example.jdbcexamples.service;

import org.example.jdbcexamples.dox.Account;
import org.example.jdbcexamples.repository.AccountPessRepository;
import org.example.jdbcexamples.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountPessRepository accountPessRepository;

    @Transactional
    public void update(String uid, float spend) {
        // 先查询
        Account account = accountRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("账户不存在"));
        log.debug("thread: {}; balance: {}", Thread.currentThread().getName(), account.getBalance());
        // 再阻塞，模拟耗时业务
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 更新
        float balance = account.getBalance() - spend;
        if (balance < 0) {
            throw new RuntimeException("账户余额不足");
        }
        account.setBalance(balance);
        // 同步
        accountRepository.save(account);
    }

    @Transactional
    public void updatePess(String uid, float spend) {
        // 基于for update悲观锁获取
        var account = accountPessRepository.findByIdPess(uid)
                .orElseThrow(() -> new RuntimeException("账户不存在"));
        log.debug("thread: {}; balance: {}", Thread.currentThread().getName(), account.getBalance());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        float balance = account.getBalance() - spend;
        if (balance < 0) {
            throw new RuntimeException("账户余额不足");
        }
        account.setBalance(balance);
        // 同步
        accountPessRepository.save(account);
    }

    @Transactional
    public void updateBalance(String uid, float spend) {
        var account = accountPessRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("账户不存在"));
        log.debug("thread: {}; balance: {}", Thread.currentThread().getName(), account.getBalance());
        // 数据库执行update语句时，添加行级排他锁
        var result = accountPessRepository.updateBalance(uid, spend);
        if (result == 0) {
            throw new RuntimeException("账户余额不足");
        }
        var account2 = accountPessRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("账户不存在"));
        log.debug("thread: {}; balance: {}", Thread.currentThread().getName(), account2.getBalance());
    }
}
