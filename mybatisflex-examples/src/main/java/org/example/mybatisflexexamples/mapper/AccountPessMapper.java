package org.example.mybatisflexexamples.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.update.UpdateChain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.example.mybatisflexexamples.entity.AccountPess;
import org.example.mybatisflexexamples.entity.table.AccountPessTableDef;

@Mapper
public interface AccountPessMapper extends BaseMapper<AccountPess> {

    default AccountPess getAccountForUpdate(long id) {
        return QueryChain.of(this)
                .select()
                .where(QueryMethods.column("id").eq(id))
                .forUpdate()
                .one();
    }

    @Update("""
            update account_pess a set a.balance=(a.balance - #{spend})
            where a.id=#{uid} and a.balance >= #{spend}
            """)
    int updateBalance(long uid, double spend);

    default boolean updateBalance2(long uid, double spend) {
        return UpdateChain.of(this)
                // a.balance = a.balance - #{spend}
                .set(AccountPessTableDef.ACCOUNT_PESS.BALANCE, AccountPessTableDef.ACCOUNT_PESS.BALANCE.subtract(spend))
                .where(AccountPessTableDef.ACCOUNT_PESS.ID.eq(uid))
                // and a.balance >= #{spend}
                .and(AccountPessTableDef.ACCOUNT_PESS.BALANCE.ge(spend))
                .update();
    }
}
