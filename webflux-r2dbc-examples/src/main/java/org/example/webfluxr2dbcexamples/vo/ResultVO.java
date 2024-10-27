package org.example.webfluxr2dbcexamples.vo;

import org.example.webfluxr2dbcexamples.exception.Code;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ResultVO {
    private int code;
    private String message;
    private Map<String, Object> data;

    private static final ResultVO EMPTY = ResultVO.builder()
            .code(200)
            .data(Map.of())
            .build();

    public static ResultVO ok() {
        return EMPTY;
    }

    public static ResultVO success(Map<String, Object> data) {
        return ResultVO.builder().code(200).data(data).build();
    }

    public static ResultVO success(Code code, Map<String, Object> data) {
        return ResultVO.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .data(data)
                .build();
    }

    public static ResultVO error(int code, String msg) {
        return ResultVO.builder().code(code).message(msg).build();
    }

    public static ResultVO error(Code code) {
        return ResultVO.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();
    }
}
