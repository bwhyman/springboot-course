package org.example.springsecurityexamples.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springsecurityexamples.exception.Code;
import org.example.springsecurityexamples.filter.GlobalExceptionHandlingFilter;
import org.example.springsecurityexamples.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
    private final JwtFilter jwtFilter;
    private final GlobalExceptionHandlingFilter  globalExceptionHandlingFilter;
    private final ResponseComponent errorResponse;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 前后端解耦下，csrf/session必须手动关闭
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .passwordManagement(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 公开接口，直接通过
                        .requestMatchers("/api/open/**").permitAll()
                        // 粗粒度角色路径
                        .requestMatchers("/api/manager/**").hasRole(Roles.MANAGER)
                        .requestMatchers("api/user/**").hasAnyRole(Roles.USER)
                        // 所有`/api/`接口，必须登录
                        .requestMatchers("/api/**").authenticated()
                        // 其他`/swagger-ui/`等静态资源路径，直接通过
                        .anyRequest().permitAll()
                )
                .exceptionHandling(exc ->
                        // 未登录兜底
                        exc.authenticationEntryPoint((request, response, ex) ->
                                        errorResponse.response(response, Code.UNAUTHORIZED))
                                // 无权限兜底
                                .accessDeniedHandler((request, response, ex) -> errorResponse.response(response, Code.FORBIDDEN)))
                .addFilterBefore(jwtFilter, AnonymousAuthenticationFilter.class)
                .addFilterBefore(globalExceptionHandlingFilter, LogoutFilter.class)
                .build();
    }

    // 取消默认生成的随机密码
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
