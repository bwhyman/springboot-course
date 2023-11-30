package com.example.springmvcexamples.example04.passwordencoder.service;

import com.example.springmvcexamples.example04.passwordencoder.entity.User04;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService04 {

    public User04 getUser(String userName) {
        return "BO".equals(userName)
                ? User04.builder()
                .userName("BO")
                .password("$2a$10$vbic.eN8nCmnzExjVIUUwOKsIAz0.NGEYC/IGwjWJHSCC8s37Kn9G")
                .build()
                : null;
    }
}
