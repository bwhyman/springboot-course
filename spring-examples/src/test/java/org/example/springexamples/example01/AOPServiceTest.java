package org.example.springexamples.example01;


import org.example.springexamples.example01.aop.AOPService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AOPServiceTest {
    @Autowired
    private AOPService aopService;

    @Test
    public void test_get() {
        aopService.get();
        log.debug(aopService.getClass().getName());
    }
}
