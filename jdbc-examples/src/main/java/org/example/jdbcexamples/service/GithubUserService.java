package org.example.jdbcexamples.service;

import org.example.jdbcexamples.dox.GithubUser;
import org.example.jdbcexamples.repository.GithubUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubUserService {
    private final GithubUserRepository githubUserRepository;
    private final ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase();

    public List<GithubUser> listGithubUsers(GithubUser optional) {
        Example<GithubUser> example = Example.of(optional, matcher);
        return (List<GithubUser>) githubUserRepository.findAll(example);
    }
}
