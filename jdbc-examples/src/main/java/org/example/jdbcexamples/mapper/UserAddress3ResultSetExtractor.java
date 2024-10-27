package org.example.jdbcexamples.mapper;

import org.example.jdbcexamples.dox.Address;
import org.example.jdbcexamples.dto.UserAddress3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserAddress3ResultSetExtractor implements ResultSetExtractor<UserAddress3> {
    @Override
    public UserAddress3 extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Address> addresses = new ArrayList<>();
        UserAddress3.UserAddress3Builder builder = UserAddress3.builder();
        while (rs.next()) {
            builder.id(rs.getString("user_id"));
            builder.name(rs.getString("name"));
            builder.createTime(rs.getObject("u.create_time", LocalDateTime.class));
            builder.updateTime(rs.getObject("u.update_time", LocalDateTime.class));
            Address a = Address.builder().id(rs.getString("id"))
                    .detail(rs.getString("detail"))
                    .userId(rs.getString("user_id"))
                    .createTime(rs.getObject("a.create_time", LocalDateTime.class))
                    .updateTime(rs.getObject("a.update_time", LocalDateTime.class))
                    .build();
            addresses.add(a);
        }
        return builder.addresses(addresses).build();
    }
}
