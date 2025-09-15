package org.example.springsecurityexamples;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
public class PasswordTest {
    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void test() {
        var account = "2046204601";
        var password = encoder.encode(account);
        log.debug(password);
    }
}
