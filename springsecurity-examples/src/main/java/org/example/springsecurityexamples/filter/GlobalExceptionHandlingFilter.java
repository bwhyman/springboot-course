package org.example.springsecurityexamples.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springsecurityexamples.exception.XException;
import org.example.springsecurityexamples.security.ResponseComponent;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalExceptionHandlingFilter extends OncePerRequestFilter {
    private final ResponseComponent responseComponent;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (XException e) {
            if(e.getCode() != null) {
                responseComponent.response(response, e.getCode());
            }
            responseComponent.response(response, e.getCodeN(), e.getMessage());
        }
    }
}
