package org.example.springmvcexamples.example08.openapi;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User08 {
    private String id;
    private String name;
    private String account;
    private String password;
}
