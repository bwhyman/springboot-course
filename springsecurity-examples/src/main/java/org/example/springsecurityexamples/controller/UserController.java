package org.example.springsecurityexamples.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {

    @PreAuthorize("hasAuthority(T(org.example.springsecurityexamples.entity.User).ADMIN)")
    @GetMapping("test")
    public String test(@AuthenticationPrincipal String id) {
        log.debug(id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.debug(principal.toString());
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            log.debug(authority.getAuthority());
        }

        return "admin";
    }
}
