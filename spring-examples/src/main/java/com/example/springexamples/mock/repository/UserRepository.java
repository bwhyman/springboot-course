package com.example.springexamples.mock.repository;


import com.example.springexamples.mock.dox.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    User save(User user);

    User findById(String id);
}
