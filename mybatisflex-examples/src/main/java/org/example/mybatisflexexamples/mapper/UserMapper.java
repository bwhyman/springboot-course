package org.example.mybatisflexexamples.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.row.Row;
import org.apache.ibatis.annotations.Mapper;
import org.example.mybatisflexexamples.dto.UserAddressDTO;
import org.example.mybatisflexexamples.dto.UserAddressesJsonDTO;
import org.example.mybatisflexexamples.entity.Address;
import org.example.mybatisflexexamples.entity.User;
import org.example.mybatisflexexamples.entity.table.AddressTableDef;
import org.example.mybatisflexexamples.entity.table.UserTableDef;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    default UserAddressDTO getUserAddressDTO(long uid) {
        List<Row> rows = QueryChain.of(this)
                .select(UserTableDef.USER.DEFAULT_COLUMNS,
                        AddressTableDef.ADDRESS.DEFAULT_COLUMNS)
                .from(UserTableDef.USER)
                .join(AddressTableDef.ADDRESS)
                .on(UserTableDef.USER.ID.eq(AddressTableDef.ADDRESS.USER_ID))
                .where(UserTableDef.USER.ID.eq(uid))
                .listAs(Row.class);
       //
        User u = rows.stream()
                .findFirst()
                .map(row -> User.builder()
                        .id(row.getLong("user_x$id"))
                        .name(row.getString("name"))
                        .createTime(row.getLocalDateTime("user_x$create_time"))
                        .build())
                .orElse(null);
        //
        List<Address> addresses = rows.stream()
                .map(row -> Address.builder()
                        .id(row.getLong("address$id"))
                        .detail(row.getString("detail"))
                        .userId(row.getLong("user_id"))
                        .createTime(row.getLocalDateTime("address$create_time"))
                        .build())
                .toList();

        return UserAddressDTO.builder()
                .user(u)
                .addresses(addresses)
                .build();
    }

    default UserAddressesJsonDTO getUserAddressesByUidQueryJson(long uid) {
        return QueryChain.of(this)
                .select("""
                        json_object('id', u.id, 'name', u.name) as user
                        """)
                .select("""
                        json_arrayagg(json_object('id', a.id, 'detail', a.detail)) as addresses
                        """)
                .from(UserTableDef.USER.as("u"))
                .from(AddressTableDef.ADDRESS.as("a"))
                .where(UserTableDef.USER.ID.eq(uid))
                .oneAs(UserAddressesJsonDTO.class);
    }
}
