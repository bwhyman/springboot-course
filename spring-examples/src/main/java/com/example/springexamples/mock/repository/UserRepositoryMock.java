package com.example.springexamples.mock.repository;


import com.example.springexamples.mock.dox.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryMock {
    public User save(User user);

    public User findById(String id);
}
