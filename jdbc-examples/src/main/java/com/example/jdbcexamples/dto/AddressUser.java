package com.example.jdbcexamples.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressUser {
    private String id;
    private String userId;
    private String name;
    private String detail;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
