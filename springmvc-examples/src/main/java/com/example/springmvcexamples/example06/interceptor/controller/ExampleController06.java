package com.example.springmvcexamples.example06.interceptor.controller;

import com.example.springmvcexamples.example05.jwt.JWTComponent;
import com.example.springmvcexamples.example06.interceptor.entity.User06;
import com.example.springmvcexamples.example06.interceptor.service.UserService06;
import com.example.springmvcexamples.vo.ResultVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/example06/")
@RequiredArgsConstructor
public class ExampleController06 {
    private final UserService06 userService;
    private final PasswordEncoder encoder;
    private final JWTComponent jwtComponent;

    @PostMapping("login")
    public ResultVO login(@RequestBody User06 user, HttpServletResponse response) {
        User06 u = userService.getUser(user.getUserName());
        if (u == null || !encoder.matches(user.getPassword(), u.getPassword())) {
            return ResultVO.error(401, "用户名密码错误");
        }
        // 登录成功，模拟获取用户id角色等信息，加密
        String result = jwtComponent.encode(Map.of("uid", u.getId(), "role", u.getRole()));
        log.debug(result);
        // 以指定键值对，置于响应header
        response.addHeader("token", result);
        return ResultVO.success(Map.of());
    }

    @GetMapping("admin/welcome")
    public ResultVO getWelcome(@RequestAttribute("role") String role) {
        log.debug(role);
        return ResultVO.success(Map.of("msg", "欢迎回来"));
    }
}
