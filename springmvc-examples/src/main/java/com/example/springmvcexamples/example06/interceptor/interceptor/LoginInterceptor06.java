package com.example.springmvcexamples.example06.interceptor.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springmvcexamples.exception.Code;
import com.example.springmvcexamples.exception.XException;
import com.example.springmvcexamples.component.JWTComponent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoginInterceptor06 implements HandlerInterceptor {
    private final JWTComponent jwtComponent;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null) {
            throw XException.builder().code(Code.UNAUTHORIZED).build();
        }
        DecodedJWT decodedJWT = jwtComponent.decode(token);
        String uid = decodedJWT.getClaim("uid").asString();
        String role = decodedJWT.getClaim("role").asString();

        request.setAttribute("uid", uid);
        request.setAttribute("role", role);
        return true;
    }
}
