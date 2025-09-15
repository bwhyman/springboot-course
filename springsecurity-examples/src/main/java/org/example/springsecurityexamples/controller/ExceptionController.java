package org.example.springsecurityexamples.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.springsecurityexamples.exception.Code;
import org.example.springsecurityexamples.exception.XException;
import org.example.springsecurityexamples.vo.ResultVO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    // 捕获方法级无权限
    @ExceptionHandler(AccessDeniedException.class)
    public ResultVO handleAccessDeniedException(AccessDeniedException exception) {
        return ResultVO.error(Code.FORBIDDEN);
    }

    @ExceptionHandler(XException.class)
    public ResultVO handleXException(XException exception) {
        if (exception.getCode() != null) {
            return ResultVO.error(exception.getCode());
        }
        return ResultVO.error(Code.ERROR, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception exception) {
        return ResultVO.error(Code.ERROR, exception.getMessage());
    }
}
