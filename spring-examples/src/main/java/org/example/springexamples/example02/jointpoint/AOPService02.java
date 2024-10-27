package org.example.springexamples.example02.jointpoint;

import org.springframework.stereotype.Component;

@Component
public class AOPService02 {
    public String hello(String name) {
        return "welcome " + name;
    }
}
