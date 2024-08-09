package com.example.springexamples.exception;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XException extends RuntimeException {
    private String message;
}
