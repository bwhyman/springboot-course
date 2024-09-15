package com.example.jdbcexamples.repository;

import com.example.jdbcexamples.dox.AccountPess;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountPessRepository extends CrudRepository<AccountPess, String> {
    @Query("""
            select * from account_pess a where a.id=:uid for update
            """)
    Optional<AccountPess> findByIdPess(String uid);

    @Modifying
    @Query("""
            update account_pess a
            set a.balance=(a.balance-:spend)
            where a.id=:uid and a.balance>=:spend
            """)
    int updateBalance(String uid, float spend);
}
