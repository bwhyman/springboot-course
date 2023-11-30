package com.example.springmvcexamples.example02.handlingexception.controller;

import com.example.springmvcexamples.example02.handlingexception.exception.XException;
import com.example.springmvcexamples.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController02 {
    @ExceptionHandler(XException.class)
    public ResultVO handleValidException(XException exception) {
        return ResultVO.error(exception.getCode(), exception.getMessage());
    }

}
