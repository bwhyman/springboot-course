package com.example.springexamples.example03;

import com.example.springexamples.example03.aopadvanced.AOPService03;
import com.example.springexamples.exception.XException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AOPServiceTest {
    @Autowired
    private AOPService03 aopService03;

    @Test
    public void test_getUser() {
        aopService03.getUser();
    }

    @Test
    public void test_getAdmin() {
        // Assertions.assertThrows(XException.class, () -> aopService03.getAdmin());
        aopService03.getAdmin();
    }
}
