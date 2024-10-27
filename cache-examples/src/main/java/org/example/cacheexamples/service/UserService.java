package org.example.cacheexamples.service;

import org.example.cacheexamples.dox.User;
import org.example.cacheexamples.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Cacheable(value = "user", key = "#uid")
    public User getUser(String uid) {
        User user = userRepository.getUser(uid);
        log.debug("called UserService getUser() user: {}", user);
        return user;
    }
    @Cacheable(value = "users")
    public List<User> listUsers() {
        return userRepository.listUsers();
    }

    @CachePut(value = "user", key = "#user.id")
    // 以键值对缓存一个集合对象时，缓存对象是一个整体。无法修改其中某一个元素
    // 因此清空整个集合缓存
    @CacheEvict(value = "users", allEntries = true)
    public User updateUser(User user) {
        User u = userRepository.updateUser(user);
        log.debug("updateUser(), user: {}", u);
        return user;
    }

    @CacheEvict(value = "user", key = "#uid")
    public void delUser(String uid) {
        // 从缓存删除，没有调用模拟的持久层删除
    }
}
