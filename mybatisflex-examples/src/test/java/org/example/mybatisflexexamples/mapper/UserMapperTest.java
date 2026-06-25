package org.example.mybatisflexexamples.mapper;

import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.row.Row;
import lombok.extern.slf4j.Slf4j;
import org.example.mybatisflexexamples.entity.User;
import org.example.mybatisflexexamples.entity.table.AddressTableDef;
import org.example.mybatisflexexamples.entity.table.UserTableDef;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void save() {
        var user = User.builder().name("BO").build();
        userMapper.insertSelective(user);
    }

    @Test
    public void getUser() {
        var user = userMapper.selectOneById(31078024548416L);
        log.debug("{}", user);
    }

    @Test
    void getUserAddressDTO() {
        var users = userMapper.getUserAddressDTO(29790033938496L);
        log.debug("{}", users);
    }

    @Test
    void DbRow() {
        var ua = userMapper.getUserAddressDTO(31078024548416L);
        var u = ua.getUser();
        log.debug("{} / {}", u.getId(),u.getName());
        ua.getAddresses()
                .forEach(a -> log.debug("{} / {}", a.getId(), a.getDetail()));
    }

    @Test
    void df() {
        QueryChain.of(userMapper)
                // 查询user/address全部字段
                .select(UserTableDef.USER.DEFAULT_COLUMNS,
                        AddressTableDef.ADDRESS.DEFAULT_COLUMNS)
                .from(UserTableDef.USER)
                .join(AddressTableDef.ADDRESS)
                .on(UserTableDef.USER.ID.eq(AddressTableDef.ADDRESS.USER_ID))
                .where(UserTableDef.USER.ID.eq(31078024548416L))
                .listAs(Row.class)
                .forEach(row -> {
                    log.debug("{}", row);
                    // 字段冲突，按约定获取
                    log.debug("user_x$id: {}", row.getLong("user_x$id"));
                    log.debug("address$id: {}", row.getLong("address$id"));
                });
    }

    @Test
    void getUserAddressesByUidQueryJson() {
        var u = userMapper.getUserAddressesByUidQueryJson(29790033938496L);
        log.debug("user: {}", u.getUser());
        log.debug("addresses: {}", u.getAddresses());
    }
}