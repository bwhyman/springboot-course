package org.example.springsecurityexamples.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springsecurityexamples.exception.Code;
import org.example.springsecurityexamples.vo.ResultVO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseComponent {

    private final ObjectMapper objectMapper;
    public void response(HttpServletResponse response, Code code) {
        response(response, code.getCode(), code.getMessage());
    }

    public void response(HttpServletResponse response, int code, String message) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try(PrintWriter writer = response.getWriter()) {
            var result = objectMapper.writeValueAsString(ResultVO.error(code, message));
            writer.write(result);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
