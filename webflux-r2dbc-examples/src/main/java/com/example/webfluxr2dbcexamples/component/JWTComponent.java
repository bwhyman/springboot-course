package com.example.webfluxr2dbcexamples.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.webfluxr2dbcexamples.exception.Code;
import com.example.webfluxr2dbcexamples.exception.XException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JWTComponent {
    // 私钥
    @Value("${my.secretkey}")
    private String secretkey;
    public String encode(Map<String, Object> map) {
        LocalDateTime time = LocalDateTime.now().plusMonths(1);
        return JWT.create()
                .withPayload(map)
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(time.atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC256(secretkey));
    }

    public Mono<DecodedJWT> decode(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretkey)).build().verify(token);
            return Mono.just(decodedJWT);
        } catch (TokenExpiredException | SignatureVerificationException | JWTDecodeException e) {
            Code code = Code.FORBIDDEN;
            if (e instanceof TokenExpiredException) {
                code = Code.TOKEN_EXPIRED;
            }
            return Mono.error(XException.builder().code(code).build());
        }
    }
}
