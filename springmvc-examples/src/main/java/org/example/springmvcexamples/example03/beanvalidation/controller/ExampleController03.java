package org.example.springmvcexamples.example03.beanvalidation.controller;

import org.example.springmvcexamples.example03.beanvalidation.dox.User03;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/example03/")
@Validated
public class ExampleController03 {

    @PostMapping("users")
    public void postUser(@Valid @RequestBody User03 user) {
    }

    @GetMapping("users/{uid}/file")
    public void getTypeMismatchException(@PathVariable int uid) {
    }

    @GetMapping("users/{owner}")
    public void getViolationException(
            @Size(min = 2, max = 6, message = "用户信息错误")
            @PathVariable String owner) {
    }

}
