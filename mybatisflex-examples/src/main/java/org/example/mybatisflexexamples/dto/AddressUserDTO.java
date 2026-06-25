package org.example.mybatisflexexamples.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressUserDTO {
    private Long addressId;
    private Long userId;
    private String name;
    private String detail;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
