package com.example.jdbcexamples.mapper;

import com.example.jdbcexamples.dox.Address;
import com.example.jdbcexamples.dox.User;
import com.example.jdbcexamples.dto.AddressUser2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Slf4j
public class AddressUser2RowMapper implements RowMapper<AddressUser2> {

    @Override
    public AddressUser2 mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = User.builder()
                .id(rs.getString("u.id"))
                .name(rs.getString("name"))
                .createTime(rs.getObject("u.create_time", LocalDateTime.class))
                .updateTime(rs.getObject("u.update_time", LocalDateTime.class))
                .build();
        Address address = Address.builder()
                .id(rs.getString("a.id"))
                .detail(rs.getString("detail"))
                .userId(rs.getString("user_id"))
                .createTime(rs.getObject("u.create_time", LocalDateTime.class))
                .updateTime(rs.getObject("u.update_time", LocalDateTime.class))
                .build();
        return AddressUser2.builder()
                .user(user)
                .address(address)
                .build();
    }
}
