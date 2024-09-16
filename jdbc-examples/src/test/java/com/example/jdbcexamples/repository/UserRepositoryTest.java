package com.example.jdbcexamples.repository;

import com.example.jdbcexamples.dox.User;
import com.example.jdbcexamples.dto.AddressUser;
import com.example.jdbcexamples.dto.UserAddress;
import com.example.jdbcexamples.dto.UserAddress3;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@SpringBootTest
@Slf4j
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUser() {
        User user = User.builder().name("BO").build();
        userRepository.save(user);
    }

    @Test
    public void findById() {
        userRepository.findById("1530375756270878723")
                .ifPresent(user -> log.debug("{}", user));
    }

    @Test
    void findAll() {
        int pageSize = 5;
        int page = 4;
        userRepository.findAll((page - 1) * pageSize, 5)
                .forEach(user -> log.debug(user.toString()));
    }

    @Test
    void findAllPageable() {
        int pageSize = 5;
        int page = 4;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        userRepository.findAll(pageable)
                .forEach(user -> log.debug(user.toString()));
    }

    @Test
    void findByIdDesc() {
        int pageSize = 5;
        int page = 1;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        userRepository.findByIdDesc(pageable)
                .forEach(user -> log.debug(user.toString()));
    }

    @Test
    void findAddressUser() {
        AddressUser addreddUser = userRepository.findByAddressId("1057571239761793024");
        log.debug(addreddUser.toString());
    }

    @Test
    void findUserAddress() {
        UserAddress userAddress = userRepository.findUserAddress("1057571239761793024");
        log.debug("{}", userAddress.getUser());
        userAddress.getAddresses().forEach(a -> log.debug("{}", a));
    }

    @Test
    void findUserAddress3() {
        UserAddress3 u = userRepository.findUserAddress3("1057571239761793024");
        log.debug("{}", u);
    }
}