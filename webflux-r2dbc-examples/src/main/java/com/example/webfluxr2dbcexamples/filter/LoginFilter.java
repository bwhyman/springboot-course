package com.example.webfluxr2dbcexamples.filter;

import com.example.webfluxr2dbcexamples.component.JWTComponent;
import com.example.webfluxr2dbcexamples.exception.Code;
import com.example.webfluxr2dbcexamples.exception.XException;
import com.example.webfluxr2dbcexamples.vo.RequestConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
@Order(1)
@RequiredArgsConstructor
public class LoginFilter  implements WebFilter {
    private final PathPattern path = new PathPatternParser().parse("/api/**");
    private final List<PathPattern> excludesS = List.of(new PathPatternParser().parse("/api/login"));
    private final JWTComponent jwtComponent;
    private final ResponseHelper responseHelper;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 排除
        for (PathPattern p : excludesS) {
            if (p.matches(request.getPath().pathWithinApplication())) {
                return chain.filter(exchange);
            }
        }
        // 非过滤路径，按异常处理
        if (!path.matches(request.getPath().pathWithinApplication())) {
            return responseHelper.response(Code.BAD_REQUEST, exchange);
        }
        String token = request.getHeaders().getFirst(RequestConstant.TOKEN);
        if (token == null) {
            return responseHelper.response(Code.UNAUTHORIZED, exchange);
        }
        return jwtComponent.decode(token)
                .flatMap(decode -> {
                    Map<String, Object> attributes = exchange.getAttributes();
                    attributes.put(RequestConstant.UID, decode.getClaim(RequestConstant.UID).asString());
                    attributes.put(RequestConstant.ROLE, decode.getClaim(RequestConstant.ROLE).asString());
                    return chain.filter(exchange);
                })
                .onErrorResume(e -> responseHelper.response(((XException) e).getCode(), exchange));
    }
}
