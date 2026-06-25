package org.example.mybatisflexexamples.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mybatisflexexamples.entity.Account;
import org.example.mybatisflexexamples.entity.AccountPess;
import org.example.mybatisflexexamples.mapper.AccountMapper;
import org.example.mybatisflexexamples.mapper.AccountPessMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountMapper accountMapper;
    private final AccountPessMapper accountPessMapper;

    @Transactional
    public void update(long aid, double spend) {
        Account account = accountMapper.selectOneById(aid);
        if (account == null) {
            throw new RuntimeException("账号不存在");
        }
        log.debug("thread name: {}; balance: {}", Thread.currentThread(), account.getBalance());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException _) {
        }
        account.setBalance(account.getBalance() - spend);
        int result = accountMapper.update(account);
        // 更新记录条数
        if (result == 0) {
            throw new RuntimeException("更新失败");
        }
    }

    @Transactional
    public void updatePess(long aid, double spend) {
        AccountPess account = accountPessMapper.getAccountForUpdate(aid);
        if (account == null) {
            throw new RuntimeException("账号不存在");
        }
        log.debug("thread name: {}; balance: {}", Thread.currentThread(), account.getBalance());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException _) {
        }
        double balance = account.getBalance() - spend;
        if (balance < 0) {
            throw new RuntimeException("账户余额不足");
        }
        account.setBalance(balance);
        int result = accountPessMapper.update(account);
        // 更新记录条数
        if (result == 0) {
            throw new RuntimeException("更新失败");
        }
    }
}
