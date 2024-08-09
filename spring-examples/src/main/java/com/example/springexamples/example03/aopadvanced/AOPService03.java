package com.example.springexamples.example03.aopadvanced;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@MyAuthority
@Slf4j
public class AOPService03 {
    public void getUser() {
        log.debug("getUser()");
    }

    @MyAuthority(value = {
            MyAuthority.MyAuthorityType.ADMIN,
            MyAuthority.MyAuthorityType.SUPER_ADMIN})
    public void getAdmin() {
        log.debug("getAdmin()");
    }
}
