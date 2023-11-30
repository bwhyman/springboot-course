package com.example.jdbcexamples.repository;

import com.example.jdbcexamples.dox.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
}
