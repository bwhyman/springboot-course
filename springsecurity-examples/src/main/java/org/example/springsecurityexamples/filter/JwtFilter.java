package org.example.springsecurityexamples.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springsecurityexamples.exception.Code;
import org.example.springsecurityexamples.exception.XException;
import org.example.springsecurityexamples.security.JWTComponent;
import org.example.springsecurityexamples.security.Tokens;
import org.example.springsecurityexamples.security.UserDetails;
import org.springframework.http.server.PathContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JWTComponent jwtComponent;

    private final PathPattern exclude = new PathPatternParser()
            .parse("/api/open/**");
    // 排除指定路径请求
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return exclude.matches(PathContainer.parsePath(request.getServletPath()));
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = request.getHeader(Tokens.TOKEN);
        if(token == null){
            // 也可不抛异常，直接返回response
            throw XException.builder().code(Code.UNAUTHORIZED).build();
        }
        //
        DecodedJWT decode;
        try {
            decode = jwtComponent.decode(token);
        } catch (TokenExpiredException | SignatureVerificationException | JWTDecodeException e) {
            // 也可不抛异常，直接返回response
            throw XException.builder().code(Code.TOKEN_ERROR).build();
        }
        var uid = decode.getClaim(Tokens.UID).asLong();
        var depid =  decode.getClaim(Tokens.DEPID).asLong();
        // 取出authorities，转字符串集合
        var authorities = decode.getClaim(Tokens.AUTHORITIES).asList(String.class);
        // 转GrantedAuthority集合
        var anAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        var userDetails = UserDetails.builder()
                .uid(uid)
                .depId(depid)
                .build();
        // 创建Authentication认证对象，保存用户信息与权限
        var authentication =
                new PreAuthenticatedAuthenticationToken(userDetails, null, anAuthorities);
        // 将Authentication保存在SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
