package com.example.springmvcexamples.component;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme token = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                // 按约定命名的header中属性名称
                .name("token")
                .in(SecurityScheme.In.HEADER);
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(HttpHeaders.AUTHORIZATION);
        return new OpenAPI()
                .schemaRequirement(HttpHeaders.AUTHORIZATION, token)
                // 在全局请求添加header信息
                .addSecurityItem(securityRequirement);
    }
}
