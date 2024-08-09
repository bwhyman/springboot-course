package com.example.springexamples.mock.repository;


import com.example.springexamples.mock.dox.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryMock {
    public User save(User user) {
        return null;
    }

    public User findById(String id) {
        return null;
    }
}
