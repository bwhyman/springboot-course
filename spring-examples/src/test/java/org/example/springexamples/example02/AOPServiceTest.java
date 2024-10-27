package org.example.springexamples.example02;

import org.example.springexamples.example02.jointpoint.AOPService02;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AOPServiceTest {
    @Autowired
    private AOPService02 aopService02;

    @Test
    public void test_hello() {
        log.debug(aopService02.hello("BO"));
    }

}
