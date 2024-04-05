package com.example.springmvcexamples.example02.handlingexception.controller;

import com.example.springmvcexamples.exception.Code;
import com.example.springmvcexamples.exception.XException;
import com.example.springmvcexamples.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController02 {
    @ExceptionHandler(XException.class)
    public ResultVO handleValidException(XException exception) {
        if (exception.getCode() != null) {
            return ResultVO.error(exception.getCode());
        }
        return ResultVO.error(exception.getCodeN(),exception.getMessage());
    }
    // 兜底异常处理
    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception exception) {
        return ResultVO.error(Code.BAD_REQUEST.getCode(), exception.getMessage());
    }
}
