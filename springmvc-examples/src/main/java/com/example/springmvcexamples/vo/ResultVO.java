package com.example.springmvcexamples.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ResultVO {
    private int code;
    private String message;
    private Map<String, Object> data;

    public static ResultVO success(Map<String, Object> data) {
        return ResultVO.builder().code(200).data(data).build();
    }

    public static ResultVO error(int code, String msg) {
        return ResultVO.builder().code(code).message(msg).build();
    }
}
