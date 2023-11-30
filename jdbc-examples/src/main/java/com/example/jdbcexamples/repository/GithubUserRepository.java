package com.example.jdbcexamples.repository;

import com.example.jdbcexamples.dox.GithubUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GithubUserRepository extends CrudRepository<GithubUser, String>, QueryByExampleExecutor<GithubUser> {
}
