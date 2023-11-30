package com.example.jdbcexamples;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
class JdbcExamplesApplicationTests {

    @Test
    void contextLoads() {
        LocalDateTime parse = LocalDateTime.parse("2023-06-13T11:24:44");
        log.debug("{}", parse);
    }

}
