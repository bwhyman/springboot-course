package org.example.webfluxr2dbcexamples.controller;

import org.example.webfluxr2dbcexamples.dox.UserReact;
import org.example.webfluxr2dbcexamples.service.UserService;
import org.example.webfluxr2dbcexamples.vo.RequestConstant;
import org.example.webfluxr2dbcexamples.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/admin/")
public class AdminController {

    private final UserService userService;

    @PostMapping("users")
    public Mono<ResultVO> postUsers(@RequestBody UserReact user) {
        return userService.addUser(user)
                .thenReturn(ResultVO.ok());
    }

    @GetMapping("info")
    public Mono<ResultVO> getInfo(@RequestAttribute(RequestConstant.UID) String uid) {
        return userService.getUserById(uid)
                .map(ResultVO::success);
    }
}
