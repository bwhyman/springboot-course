package com.example.webfluxr2dbcexamples.service;

import com.example.webfluxr2dbcexamples.dox.UserReact;
import com.example.webfluxr2dbcexamples.exception.Code;
import com.example.webfluxr2dbcexamples.exception.XException;
import com.example.webfluxr2dbcexamples.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<UserReact> getUser(String account) {
        return userRepository.findByAccount(account);
    }

    public Mono<UserReact> getUserById(String uid) {
        return userRepository.findById(uid);
    }

    @Transactional
    public Mono<UserReact> addUser(UserReact user) {
        return userRepository.findByAccount(user.getAccount())
                // 如果已存在，将异常封装到订阅对象。由统一异常处理
                .handle((u, sink) ->
                        sink.error(XException.builder()
                                .codeN(Code.ERROR)
                                .message("用户已存在")
                                .build())
                )
                .cast(UserReact.class)
                // 如果不存在
                .switchIfEmpty(Mono.defer(() -> {
                    user.setPassword(passwordEncoder.encode(user.getAccount()));
                    return userRepository.save(user);
                }));
    }

    public Mono<List<UserReact>> listUsers(String role) {
        return userRepository.findByRole(role).collectList();
    }
}
