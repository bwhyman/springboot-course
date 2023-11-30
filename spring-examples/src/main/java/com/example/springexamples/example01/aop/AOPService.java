package com.example.springexamples.example01.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AOPService {
    public void get() {
      log.debug("AOPService");
    }
}
