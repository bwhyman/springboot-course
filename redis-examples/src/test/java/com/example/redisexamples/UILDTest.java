package com.example.redisexamples;

import com.example.redisexamples.component.ULID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UILDTest {
    @Autowired
    private ULID ulid;
    @Test
    void nextTest() {
        log.debug(ulid.nextULID());
        log.debug(ulid.nextULID());
        log.debug(ulid.nextULID());
    }
}
