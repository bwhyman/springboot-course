package com.example.jdbcexamples.service;

import com.example.jdbcexamples.dox.Account;
import com.example.jdbcexamples.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public Account update(String uid, float expire) {
        Account account = accountRepository.findById(uid).orElseThrow(() -> new RuntimeException("账户不存在"));
        float balance = account.getBalance() - expire;
        if (balance < 0) {
            throw new RuntimeException("账户余额不足");
        }
        account.setBalance(balance);
        return accountRepository.save(account);
    }
}
