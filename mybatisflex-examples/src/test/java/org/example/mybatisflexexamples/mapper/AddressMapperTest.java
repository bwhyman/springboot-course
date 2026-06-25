package org.example.mybatisflexexamples.mapper;

import com.mybatisflex.core.paginate.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.mybatisflexexamples.dto.AddressUser2DTO;
import org.example.mybatisflexexamples.dto.AddressUserDTO;
import org.example.mybatisflexexamples.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class AddressMapperTest {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    void  insertAddress() {
        var a1 = Address.builder()
                .detail("956")
                .userId(31078024548416L)
                .build();
        var a2 = Address.builder()
                .detail("1104")
                .userId(31078024548416L)
                .build();
        addressMapper.insertSelective(a1);
        addressMapper.insertSelective(a2);
    }
    @Test
    void  updateAddress() {
        var address = Address.builder()
                .id(31084804726848L)
                .detail("1005")
                .build();
        addressMapper.update(address);
    }

    @Test
    void  updateAddress2() {
        var address = Address.builder()
                .id(29958394357824L)
                .detail("1005")
                .build();
        addressMapper.update(address, false);
    }

    @Test
    void listAddresses() {
        addressMapper.listAddressesByUid(29790033938496L)
                .forEach(a -> log.debug(a.toString()));
    }

    @Test
    void listAddressesByUidByQuery() {
        addressMapper.listAddressesByUidByQuery(29790033938496L)
                .forEach(a -> log.debug(a.toString()));
    }

    @Test
    void getAddressUserByAidByQueryDef() {
        AddressUserDTO a = addressMapper.getAddressUserByAidByQueryDef(31084804726848L);
        log.debug(a.toString());
    }

    @Test
    void getAddressUser2ByAidByQueryDef() {
        AddressUser2DTO a = addressMapper.getAddressUser2ByAidByQueryDef(31084804726848L);
        log.debug(a.toString());
    }

    @Test
    void testListAddresses() {
        // 每页5条，第一页
        Page<Address> page = Page.of(1, 5);
        Page<Address> addressesPage = addressMapper.listAddresses(29790033938496L, page);
        log.debug("TotalRow: {}", addressesPage.getTotalRow());
        log.debug("TotalPage: {}", addressesPage.getTotalPage());
        List<Address> addresses = addressesPage.getRecords();
        log.debug("addresses.size: {}", addresses.size());
    }

    @Test
    void ListAddressesSortedByAidDesc() {
        List<Address> addresses = addressMapper.ListAddressesSortedByAidDesc(29790033938496L);
        addresses.forEach(a -> log.debug("aid: {}", a.getId()));
    }
}