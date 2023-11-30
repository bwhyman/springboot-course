package com.example.springmvcexamples.example05;

import com.example.springmvcexamples.example05.jwt.JWTComponent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@Slf4j
public class JWTTest {
    @Autowired
    private JWTComponent jwtComponent;

    @Test
    public void test() throws InterruptedException {
        String token = jwtComponent.encode(Map.of("uid", "1384896304762638307", "role", 9));
        log.debug(token);
        log.debug("{}", token.length());
        String  uid = jwtComponent.decode(token).getClaim("uid").asString();
        log.debug("{}", uid);

        Thread.sleep(15000);
        String uid2 = jwtComponent.decode(token).getClaim("uid").asString();
        log.debug("{}", uid2);
    }

}
