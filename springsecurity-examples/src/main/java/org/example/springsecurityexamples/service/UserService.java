package org.example.springsecurityexamples.service;

import lombok.extern.slf4j.Slf4j;
import org.example.springsecurityexamples.entity.User;
import org.example.springsecurityexamples.security.Authorities;
import org.example.springsecurityexamples.security.Roles;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {
    public User getUser(String account) {
        if(account.equals("2046204601")) {
            // 模拟2个权限，一个角色
            List<String> authorities = new ArrayList<>();
            authorities.add(Authorities.MANAGER_READ);
            authorities.add(Authorities.MANAGER_CREATE);
            return User.builder()
                    .id(1415648458976919552L)
                    .departmentId(1415649651350437888L)
                    .name("BO")
                    .password("$2a$10$6lqsVMLKAzMyXfwMIxsbzObqtgWB/WfGzp/Ic6X7GNLBj5CzA3Ng6")
                    .role(Roles.MANAGER)
                    .authorities(authorities)
                    .build();
        }
        return null;
    }
}
