package com.example.jdbcexamples.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @CreatedBy
    private String id;
    private String detail;
    private String userId;
    @ReadOnlyProperty
    private LocalDateTime createTime;
    @ReadOnlyProperty
    private LocalDateTime updateTime;
}
