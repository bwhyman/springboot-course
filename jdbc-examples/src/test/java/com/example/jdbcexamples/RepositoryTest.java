package com.example.jdbcexamples;

import com.example.jdbcexamples.dox.Address;
import com.example.jdbcexamples.dox.User;
import com.example.jdbcexamples.dto.AddressUser;
import com.example.jdbcexamples.dto.AddressUser2;
import com.example.jdbcexamples.dto.UserAddress;
import com.example.jdbcexamples.dto.UserAddress3;
import com.example.jdbcexamples.repository.AddressRepository;
import com.example.jdbcexamples.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class RepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void saveUserTest() {
        User user = User.builder().name("BO").build();
        userRepository.save(user);
    }

    @Test
    public void saveAddressTest() {
        Address address = Address.builder().userId("1057571239761793024").detail("1001").build();
        addressRepository.save(address);
    }
    @Test
    public void findByIdTest() {
        userRepository.findById("1530375756270878723").ifPresent(user -> log.debug("{}", user));
    }
    @Test
    public void updateUserTest() {
        userRepository.findById("985072788262170624").ifPresent(u -> {
            u.setName("SUN");
            userRepository.save(u);
        });
    }

    @Test
    public void findUserAddressTest() {
        User user = userRepository.findById("1057571239761793024").orElse(null);
        List<Address> addresses = addressRepository.find("1057571239761793024");
        UserAddress userAddress = UserAddress.builder()
                .user(user)
                .addresses(addresses)
                .build();
    }

    @Test
    public void findByAIdTest() {
        AddressUser addressUser = addressRepository.findByAId("1530375756270878722");
        log.debug(addressUser.toString());
    }

    @Test
    public void findAddressUser2Test() {
        AddressUser2 addressUser2 = addressRepository.findAddressUser2("1057590557849407488");
        log.debug("{}", addressUser2.getUser());
        log.debug("{}", addressUser2.getAddress());
    }

    @Test
    public void findAddressesByUidTest() {
        userRepository.findAllByUid("1057571239761793024").forEach(addressUser -> log.debug("{}", addressUser));
    }

    @Test
    public void findByUserId() {
        for (Address address : addressRepository.findByUserId("985072788262170624")) {
            log.debug("{}", address);
        }
    }

    @Test
    public void findUserAddressByUidTest() {
        UserAddress userAddress = userRepository.findUserAddress("1057571239761793024");
        log.debug("{}", userAddress.getUser());
        userAddress.getAddresses().forEach(a -> log.debug("{}", a));
    }

    @Test
    public void findUserAddress3Test() {
        UserAddress3 u = userRepository.findUserAddress3("1057571239761793024");
        log.debug(u.toString());
    }
    @Test
    public void test5() {
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
    public void test_updateDetail() {
        // 模拟传入的局部更新数据
        Address addressSource = Address.builder().id("985074876576772096").detail("888").build();
        addressRepository.updateDetail(addressSource.getDetail(), addressSource.getId());
    }

}
