package org.example.webfluxr2dbcexamples.service;

import org.example.webfluxr2dbcexamples.dox.UserReact;
import org.example.webfluxr2dbcexamples.exception.Code;
import org.example.webfluxr2dbcexamples.exception.XException;
import org.example.webfluxr2dbcexamples.repository.UserRepository;
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
    public Mono<Void> addUser(UserReact user) {
        return userRepository.findByAccount(user.getAccount())
                // 如果用户已存在，直接抛出异常
                .flatMap(existingUser -> Mono.error(XException.builder()
                        .codeN(Code.ERROR)
                        .message("用户已存在")
                        .build()))
                // 如果用户不存在
                .switchIfEmpty(Mono.defer(() -> {
                    user.setRole(UserReact.ROLE_USER);
                    user.setPassword(passwordEncoder.encode(user.getAccount()));
                    return userRepository.save(user);
                }))
                .then();
    }

    public Mono<List<UserReact>> listUsers(String role) {
        return userRepository.findByRole(role).collectList();
    }
}
