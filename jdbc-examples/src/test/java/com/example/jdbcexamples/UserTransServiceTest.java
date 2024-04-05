package com.example.jdbcexamples;

import com.example.jdbcexamples.dox.User;
import com.example.jdbcexamples.service.UserTransService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UserTransServiceTest {
    @Autowired
    private UserTransService userTransService;

    @Test
    public void addUserTest() {
        User u = User.builder().name("transaction").build();
        User user = userTransService.addUser(u);
        log.debug("{}", user);
    }
}
