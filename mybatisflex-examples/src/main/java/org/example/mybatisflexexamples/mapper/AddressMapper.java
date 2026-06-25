package org.example.mybatisflexexamples.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryMethods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.mybatisflexexamples.dto.AddressUser2DTO;
import org.example.mybatisflexexamples.dto.AddressUserDTO;
import org.example.mybatisflexexamples.entity.Address;
import org.example.mybatisflexexamples.entity.table.AddressTableDef;
import org.example.mybatisflexexamples.entity.table.UserTableDef;

import java.util.List;

@Mapper
public interface AddressMapper extends BaseMapper<Address> {

    @Select("""
            select * from address a where a.user_id=#{uid};
            """)
    List<Address> listAddressesByUid(long uid);

    default List<Address> listAddressesByUidByQuery(long uid) {
        return QueryChain.of(this)
                .select(Address::getDetail)
                .from("address").as("a")
                .where(QueryMethods.column("a.user_id").eq(uid))
                .list();
    }

    default AddressUserDTO getAddressUserByAidByQueryDef(long aid) {
        return QueryChain.of(this)
                .select(AddressTableDef.ADDRESS.DEFAULT_COLUMNS)
                .select(UserTableDef.USER.NAME.as("name"))
                .select(AddressTableDef.ADDRESS.ID.as("addressId"))
                .select(AddressTableDef.ADDRESS.USER_ID.as("userId"))
                .from(AddressTableDef.ADDRESS)
                .from(UserTableDef.USER)
                .where(AddressTableDef.ADDRESS.ID.eq(aid))
                .oneAs(AddressUserDTO.class);
    }

    default AddressUser2DTO getAddressUser2ByAidByQueryDef(long aid) {
        return QueryChain.of(this)
                .select(AddressTableDef.ADDRESS.DEFAULT_COLUMNS)
                .select(UserTableDef.USER.DEFAULT_COLUMNS)
                .from(AddressTableDef.ADDRESS)
                .from(UserTableDef.USER)
                .where(AddressTableDef.ADDRESS.ID.eq(aid))
                .oneAs(AddressUser2DTO.class);
    }

    default Page<Address> listAddresses(long uid, Page<Address> page) {
        return QueryChain.of(this)
                .select(AddressTableDef.ADDRESS.DEFAULT_COLUMNS)
                .from(AddressTableDef.ADDRESS)
                .where(AddressTableDef.ADDRESS.USER_ID.eq(uid))
                .page(page);
    }

    default List<Address> ListAddressesSortedByAidDesc(long uid) {
        return QueryChain.of(this)
                .select(AddressTableDef.ADDRESS.DEFAULT_COLUMNS)
                .from(AddressTableDef.ADDRESS)
                .where(AddressTableDef.ADDRESS.USER_ID.eq(uid))
                .orderBy(AddressTableDef.ADDRESS.ID.desc())
                .list();
    }

}
