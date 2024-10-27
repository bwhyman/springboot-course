package org.example.springmvcexamples.example06.interceptor.service;


import org.example.springmvcexamples.example06.interceptor.dox.User06;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService06 {

    public User06 getUser(String userName) {
        return "BO".equals(userName)
                ? User06.builder().id("98416621588")
                .userName("BO")
                .role(User06.ADMIN)
                .password("$2a$10$vbic.eN8nCmnzExjVIUUwOKsIAz0.NGEYC/IGwjWJHSCC8s37Kn9G")
                .build()
                : null;
    }
}
