package com.example.springmvcexamples.example02.handlingexception.exception;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class XException extends RuntimeException {
    private int code;
    private String message;
}
