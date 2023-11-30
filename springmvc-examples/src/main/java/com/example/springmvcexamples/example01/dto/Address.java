package com.example.springmvcexamples.example01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private Integer id;
    private String detail;
    private String comment;
    private User user;
    private LocalDateTime inertTime;
}
