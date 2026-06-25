package org.example.mybatisflexexamples.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.mybatisflexexamples.entity.Address;
import org.example.mybatisflexexamples.entity.User;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressDTO {
    private User user;
    private List<Address> addresses;
}
