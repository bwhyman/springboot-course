package org.example.springmvcexamples.vo;

import org.example.springmvcexamples.exception.Code;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ResultVO {
    private int code;
    private String message;
    private Object data;

    private static final ResultVO EMPTY = ResultVO.builder()
            .code(200)
            .data(Map.of())
            .build();

    public static ResultVO ok() {
        return EMPTY;
    }

    public static ResultVO success(Object data) {
        return ResultVO.builder().code(200).data(data).build();
    }

    public static ResultVO error(Code code) {
        return ResultVO.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();
    }

    public static ResultVO error(int code, String msg) {
        return ResultVO.builder().code(code).message(msg).build();
    }

}
