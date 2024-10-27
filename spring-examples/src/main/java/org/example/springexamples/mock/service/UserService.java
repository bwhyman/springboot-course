package org.example.springexamples.mock.service;


import org.example.springexamples.mock.dox.User;
import org.example.springexamples.mock.repository.UserRepositoryMock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepositoryMock userRepositoryMock;

    public User addUser(User user) {
        return userRepositoryMock.save(user);
    }

    public User getUser(String uid) {
        return userRepositoryMock.findById(uid);
    }

}
