package org.example.springmvcexamples.example04.passwordencoder.controller;


import org.example.springmvcexamples.example04.passwordencoder.dox.User04;
import org.example.springmvcexamples.example04.passwordencoder.service.UserService04;
import org.example.springmvcexamples.exception.Code;
import org.example.springmvcexamples.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/example04/")
@RequiredArgsConstructor
public class ExampleController04 {

    private final UserService04 userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public ResultVO login(@RequestBody User04 user) {
        // 先查询用户是否存在
        User04 u = userService.getUser(user.getUserName());
        if (u == null || !passwordEncoder.matches(user.getPassword(), u.getPassword())) {
            log.debug("登录失败");
            return ResultVO.error(Code.LOGIN_ERROR);
        }
        // 登录成功，添加token等操作
        log.debug("登录成功");
        return ResultVO.success(u);
    }
}
