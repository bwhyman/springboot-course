package com.example.springmvcexamples.example03.beanvalidation.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User03 {
    private int id;
    @Size(min = 2, max = 6,
            message = "您输入的值为${validatedValue}，用户名长度必须大于{min}，小于{max}")
    private String name;
    @Min(value = 18,
            message = "您输入的值为${validatedValue}，年龄不能小于{value}")
    @Max(value = 60,
            message = "您输入的值为${validatedValue}，年龄不能大于{value}")
    private int age;
    @Email(message = "Email不合法")
    private String email;
}

