package org.example.jdbcexamples.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.jdbcexamples.dox.GithubUser;
import org.example.jdbcexamples.dto.GithubOptionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootTest
@Slf4j
class GithubUserRepositoryTest {
    @Autowired
    private GithubUserRepository githubUserRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void addGithubUserTest() {
        GithubUser user = GithubUser.builder()
                .name("SUN")
                .followers(15)
                .stars(15)
                .repos(15)
                .gender("female")
                .build();
        githubUserRepository.save(user);
    }

    @Test
    void findByDynamic() {
        GithubUser user = GithubUser.builder()
                .followers(10)
                .stars(8)
                .gender("female")
                .build();
        githubUserRepository.findByDynamic(jdbcTemplate, user)
                .forEach(u -> log.debug("{}", u));
    }

    @Test
    void findByDynamicType() {
        GithubOptionType followers = new GithubOptionType("followers",GithubOptionType.GREAT_EQUAL, 10);
        GithubOptionType stars = new GithubOptionType("stars", GithubOptionType.GREAT_EQUAL, 8);
        GithubOptionType gender = new GithubOptionType("gender", GithubOptionType.EQUAL, "female");
        githubUserRepository.findByDynamic(jdbcTemplate, List.of(followers, stars, gender))
                .forEach(u -> log.debug("{}", u));
    }
}