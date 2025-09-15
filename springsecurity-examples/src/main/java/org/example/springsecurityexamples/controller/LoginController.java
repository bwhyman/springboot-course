package org.example.springsecurityexamples.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springsecurityexamples.entity.User;
import org.example.springsecurityexamples.exception.Code;
import org.example.springsecurityexamples.security.JWTComponent;
import org.example.springsecurityexamples.security.Tokens;
import org.example.springsecurityexamples.service.UserService;
import org.example.springsecurityexamples.vo.ResultVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/open/")
@RequiredArgsConstructor
public class LoginController {
    private final JWTComponent jwtComponent;
    private final UserService userService;
    private final PasswordEncoder encoder;

    @PostMapping("login")
    public ResultVO postLogin(@RequestBody User loginUser, HttpServletResponse response) {

        var user = userService.getUser(loginUser.getAccount());
        if (user == null || !encoder.matches(loginUser.getPassword(), user.getPassword())) {
            return ResultVO.error(Code.LOGIN_ERROR);
        }
        // 将角色作为特殊authority写入token的authorities
        // 对于spring security，统一authority权限模型
        var authorities = user.getAuthorities();
        // 加`ROLE_`前缀。hasRole()方法以`ROLE_authority`判断role
        authorities.add("ROLE_" + user.getRole());
        //
        var payload = Map.of(Tokens.UID, user.getId(),
                Tokens.DEPID, user.getDepartmentId(),
                Tokens.AUTHORITIES, authorities);
        var result = jwtComponent.encode(payload);
        response.addHeader(Tokens.TOKEN, result);
        return ResultVO.ok();
    }

}
