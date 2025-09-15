package org.example.springsecurityexamples.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springsecurityexamples.security.UserDetails;
import org.example.springsecurityexamples.vo.ResultVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/manager/")
@RequiredArgsConstructor
public class ManagerController {

    @PreAuthorize("hasAnyAuthority(@auth.MANAGER_READ, @auth.MANAGER_CREATE)")
    @GetMapping("read")
    public ResultVO read(@AuthenticationPrincipal UserDetails userDetails) {
        log.debug("@AuthenticationPrincipal uid: {}", userDetails);
        return ResultVO.success("read");
    }

    @PreAuthorize("hasAnyAuthority(@auth.MANAGER_DELETE)")
    @GetMapping("delete")
    public ResultVO delete() {
        // 可获取当前线程SecurityContext对象以及Authentication认证对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.debug("@AuthenticationPrincipal uid: {}", userDetails);
        // Authentication对象中封装的权限
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            log.debug(authority.getAuthority());
        }
        return ResultVO.success("delete");
    }
}
