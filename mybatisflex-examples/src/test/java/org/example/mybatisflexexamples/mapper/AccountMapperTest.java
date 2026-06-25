package org.example.mybatisflexexamples.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.mybatisflexexamples.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class AccountMapperTest {
    @Autowired
    private AccountMapper accountMapper;

    @Test
    void update() {
        accountMapper.insertSelective(Account.builder().name("BO").balance(1000D).build());
    }
}