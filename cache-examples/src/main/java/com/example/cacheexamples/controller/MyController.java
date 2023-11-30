package com.example.cacheexamples.controller;

import com.example.cacheexamples.entity.User;
import com.example.cacheexamples.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/")
public class MyController {

    private final UserService userService;

    @GetMapping("users/{uid}")
    public User getUser(@PathVariable long uid) {
        return userService.getUser(uid);
    }

    @GetMapping("users")
    public List<User> listUsers() {
        return userService.listUsers();
    }

    @PatchMapping("users")
    public User patchUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("users/{uid}")
    public void delUser(@PathVariable long uid) {
        userService.delUser(uid);
    }
}
