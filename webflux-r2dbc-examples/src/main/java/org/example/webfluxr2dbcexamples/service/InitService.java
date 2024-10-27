package org.example.webfluxr2dbcexamples.service;

import org.example.webfluxr2dbcexamples.dox.UserReact;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitService {
    private final UserService userService;
    @Transactional
    @EventListener(classes = ApplicationReadyEvent.class)
    public Mono<Void> onApplicationReadyEvent() {
        String account = "admin";
        return userService.getUser(account)
                .switchIfEmpty(Mono.defer(() -> {
                    UserReact user = UserReact.builder()
                            .name(account)
                            .account(account)
                            .role(UserReact.ROLE_ADMIN)
                            .build();
                    return userService.addUser(user);
                })).then();

    }
}
