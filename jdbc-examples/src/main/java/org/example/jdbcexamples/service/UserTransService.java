package org.example.jdbcexamples.service;

import org.example.jdbcexamples.dox.User;
import org.example.jdbcexamples.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserTransService {
    private final UserRepository userRepository;

    @Transactional
    public User addUser(User user) {
        User u = userRepository.save(user);
        try {
            Files.readString(Path.of("A:/aa.aa"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return u;
    }
}
