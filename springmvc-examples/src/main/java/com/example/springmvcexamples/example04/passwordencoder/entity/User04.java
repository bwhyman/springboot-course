package com.example.springmvcexamples.example04.passwordencoder.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User04 {
    private String userName;
    private String password;
}
