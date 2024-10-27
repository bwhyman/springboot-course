package org.example.springmvcexamples.example08.openapi;

import org.example.springmvcexamples.component.JWTComponent;
import org.example.springmvcexamples.exception.Code;
import org.example.springmvcexamples.vo.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/example08/")
@Tag(name = "测试openapi接口文档")
public class ExampleController08 {
    private final JWTComponent jwtComponent;

    @Operation(summary = "登录",
            description = "账号密码：123。登录成功返回user对象，header返回token")
    @PostMapping("login")
    public ResultVO postLogin(@RequestBody User08 user, HttpServletResponse response) {
        if ("123".equals(user.getAccount()) && "123".equals(user.getPassword())) {
            var user08 = User08.builder().id("5454").name("BO").account("182").build();
            var token = jwtComponent.encode(Map.of("id", user08.getId()));
            response.addHeader("token", token);
            return ResultVO.success(Map.of("user", user08));
        }
        return ResultVO.error(Code.LOGIN_ERROR);
    }

    @Operation(summary = "测试请求header中是否包含token")
    @GetMapping("token")
    public ResultVO getToken(HttpServletRequest request) {
        var token = request.getHeader("token");
        if (token != null) {
            return ResultVO.ok();
        }
        return ResultVO.error(Code.BAD_REQUEST);
    }
}
