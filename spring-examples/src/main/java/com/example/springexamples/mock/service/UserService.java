package com.example.springexamples.mock.service;


import com.example.springexamples.mock.dox.User;
import com.example.springexamples.mock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(String uid) {
        return userRepository.findById(uid);
    }

}
