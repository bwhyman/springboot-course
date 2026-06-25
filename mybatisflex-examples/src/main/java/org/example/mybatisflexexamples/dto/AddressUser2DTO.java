package org.example.mybatisflexexamples.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.mybatisflexexamples.entity.Address;
import org.example.mybatisflexexamples.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressUser2DTO {
    private User user;
    private Address address;
}
