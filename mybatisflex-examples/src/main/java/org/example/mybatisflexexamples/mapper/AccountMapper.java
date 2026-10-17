package org.example.mybatisflexexamples.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.update.UpdateChain;
import org.apache.ibatis.annotations.Mapper;
import org.example.mybatisflexexamples.entity.Account;
import org.example.mybatisflexexamples.entity.table.AccountTableDef;

import java.math.BigDecimal;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    default boolean updateBalance(long uid, BigDecimal expense) {
        return UpdateChain.of(this)
                .set(AccountTableDef.ACCOUNT.BALANCE, AccountTableDef.ACCOUNT.BALANCE.subtract(expense))
                .from(AccountTableDef.ACCOUNT)
                .where(AccountTableDef.ACCOUNT.ID.eq(uid))
                .where(AccountTableDef.ACCOUNT.BALANCE.ge(expense))
                .update();
    }

    default int updateBalanceBySQL(long uid, double expense) {
        var sql = """
                update account set balance = balance - ?
                where id=? and balance >= ?
                """ ;
        return Db.updateBySql(sql, expense, uid, expense);

    }
}
