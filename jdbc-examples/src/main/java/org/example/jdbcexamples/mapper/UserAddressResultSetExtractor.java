package org.example.jdbcexamples.mapper;

import org.example.jdbcexamples.dox.Address;
import org.example.jdbcexamples.dox.User;
import org.example.jdbcexamples.dto.UserAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserAddressResultSetExtractor implements ResultSetExtractor<UserAddress> {
    @Override
    public UserAddress extractData(ResultSet rs) throws SQLException, DataAccessException {
        User user = null;
        List<Address> addresses = new ArrayList<>();
        while (rs.next()) {
            if (user == null) {
                user = User.builder()
                        .id(rs.getString("u.id"))
                        .name(rs.getString("name"))
                        .createTime(rs.getObject("u.create_time", LocalDateTime.class))
                        .updateTime(rs.getObject("u.update_time", LocalDateTime.class))
                        .build();
            }
            Address a = Address.builder().id(rs.getString("a.id"))
                    .detail(rs.getString("detail"))
                    .userId(rs.getString("user_id"))
                    .createTime(rs.getObject("a.create_time", LocalDateTime.class))
                    .updateTime(rs.getObject("a.update_time", LocalDateTime.class))
                    .build();
            addresses.add(a);
        }
        return UserAddress.builder()
                .user(user)
                .addresses(addresses)
                .build();
    }
}
