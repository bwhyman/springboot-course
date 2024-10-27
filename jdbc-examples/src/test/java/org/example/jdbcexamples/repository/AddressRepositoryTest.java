package org.example.jdbcexamples.repository;

import org.example.jdbcexamples.dox.Address;
import org.example.jdbcexamples.dox.User;
import org.example.jdbcexamples.dto.AddressUser;
import org.example.jdbcexamples.dto.AddressUser2;
import org.example.jdbcexamples.dto.UserAddress;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;


    @Test
    void find() {
        for (Address a : addressRepository.find("1057571239761793024")) {
            log.debug("{}", a);
        }
    }

    @Test
    void findByUserId() {
        for (Address a : addressRepository.findByUserId("1057571239761793024")) {
            log.debug("{}", a);
        }
    }

    @Test
    void updateAddress() {
        // 更新detail属性，并更新userid属性为null
        /*Address address = Address.builder().id(908037103134662656L).detail("110").build();
        addressRepository.save(address);*/
        // 模拟传入的局部更新数据
        Address addressSource = Address.builder().id("985074876576772096").detail("110").build();
        // 查询出全部数据
        addressRepository.findById("985074876576772096").ifPresent(result -> {
            result.setDetail(addressSource.getDetail());
            addressRepository.save(result);
        });
    }

    @Test
    void updateDetail() {
        // 模拟传入的局部更新数据
        Address addressSource = Address.builder().id("985074876576772096").detail("888").build();
        addressRepository.updateDetail(addressSource.getDetail(), addressSource.getId());
    }

    @Test
    void findAddressUserById() {
        AddressUser addressUser = addressRepository.findAddressUserById("1283819168057049188");
        log.debug(addressUser.toString());
    }

    @Test
    void findUserAddress() {
        User user = userRepository.findById("1057571239761793024").orElse(null);
        List<Address> addresses = addressRepository.find("1057571239761793024");
        UserAddress userAddress = UserAddress.builder().user(user).addresses(addresses).build();
        log.debug(userAddress.toString());
    }

    @Test
    void findAddressUser2ById() {
        AddressUser2 addressUser2 = addressRepository.findAddressUser2ById("1057590557849407488");
        log.debug("{}", addressUser2.getUser());
        log.debug("{}", addressUser2.getAddress());
    }


}