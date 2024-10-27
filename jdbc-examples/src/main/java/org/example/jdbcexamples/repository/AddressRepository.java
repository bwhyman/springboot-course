package org.example.jdbcexamples.repository;

import org.example.jdbcexamples.dox.Address;
import org.example.jdbcexamples.dto.AddressUser;
import org.example.jdbcexamples.dto.AddressUser2;
import org.example.jdbcexamples.mapper.AddressUser2RowMapper;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Address, String> {

    @Query("select * from address a where a.user_id=:uid")
    List<Address> find(String uid);

    // 可基于名称自动生成SQL语句
    List<Address> findByUserId(String uid);

    @Query("""
            select a.id as id, a.detail as detail, u.name as name, a.create_time as create_time,
            a.update_time as update_time, a.user_id as user_id
            from address a join user u on u.id = a.user_id
            where a.id=:aid
            """)
    AddressUser findAddressUserById(String aid);

    @Query(value = "select * from address a join user u on u.id = a.user_id where a.id=:aid",
            rowMapperClass = AddressUser2RowMapper.class)
    AddressUser2 findAddressUser2ById(String aid);

    @Modifying
    @Query("update address a set a.detail=:detail where a.id=:aid")
    void updateDetail(String detail, String aid);
}
