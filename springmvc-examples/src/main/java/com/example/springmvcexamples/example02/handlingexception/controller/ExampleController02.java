package com.example.springmvcexamples.example02.handlingexception.controller;

import com.example.springmvcexamples.example02.handlingexception.entity.User;
import com.example.springmvcexamples.example02.handlingexception.service.UserService02;
import com.example.springmvcexamples.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/example02/")
public class ExampleController02 {

    private final UserService02 userService02;

    @GetMapping("exception")
    public void getException() {
        userService02.readFile();
    }

    @PostMapping("login")
    public ResultVO login(@RequestBody User user) {
        if (!("BO".equals(user.getUserName()) && "123456".equals(user.getPassword()))) {
            return ResultVO.error(401, "用户名密码错误");
        }
        return ResultVO.success(Map.of());
    }

}
