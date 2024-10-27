package org.example.springmvcexamples.example02.handlingexception.controller;

import org.example.springmvcexamples.example02.handlingexception.dox.User;
import org.example.springmvcexamples.example02.handlingexception.service.UserService02;
import org.example.springmvcexamples.exception.Code;
import org.example.springmvcexamples.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
            return ResultVO.error(Code.LOGIN_ERROR);
        }
        return ResultVO.ok();
    }

}
