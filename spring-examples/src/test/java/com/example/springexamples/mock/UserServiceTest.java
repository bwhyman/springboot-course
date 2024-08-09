package com.example.springexamples.mock;

import com.example.springexamples.mock.dox.User;
import com.example.springexamples.mock.repository.UserRepositoryMock;
import com.example.springexamples.mock.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
@Slf4j
class UserServiceTest {
    @MockBean
    private UserRepositoryMock userRepositoryMock;
    @Autowired
    private UserService userService;

    @Test
    void getUser() {
        // 模拟调用findById()方法，参数为`1`时，返回指定对象
        Mockito.when(userRepositoryMock.findById("1"))
                .thenReturn(User.builder().id("1").name("BO").build());
        User u = userService.getUser("1");
        log.debug("{}", u);
        // 没有模拟，因此为空
        User u2 = userService.getUser("2");
        log.debug("{}", u2);
    }

    @Test
    void addUser() {
        Mockito.when(userRepositoryMock.save(Mockito.any()))
                .thenReturn(User.builder().id("1").name("BO").build());
        User u = userService.addUser(User.builder().name("any").build());
        log.debug("{}", u);
    }

    @Test
    void getUser2() {
        List<User> users = List.of(User.builder().id("1").name("BO").build(), User.builder().id("2").name("SUN").build());
        // 获取调用Mock组件时传入的参数，操作并返回结果对象
        Mockito.when(userRepositoryMock.findById(Mockito.anyString()))
                .thenAnswer(answer -> {
                    String uid = answer.getArgument(0);
                    return users.stream()
                            .filter(u -> u.getId().equals(uid))
                            .findFirst()
                            .orElse(null);
                });

        User user = userService.getUser("2");
        log.debug("{}", user);
        // 不存在
        User user2 = userService.getUser("100");
        log.debug("{}", user2);
    }

}