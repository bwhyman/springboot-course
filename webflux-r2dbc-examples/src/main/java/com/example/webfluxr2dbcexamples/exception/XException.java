package com.example.webfluxr2dbcexamples.exception;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XException extends RuntimeException{
    private Code code;
    private int codeN;
    private String message;
}
