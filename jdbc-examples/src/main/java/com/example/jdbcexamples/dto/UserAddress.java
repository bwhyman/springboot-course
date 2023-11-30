package com.example.jdbcexamples.dto;

import com.example.jdbcexamples.dox.Address;
import com.example.jdbcexamples.dox.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddress {
    private User user;
    private List<Address> addresses;
}
