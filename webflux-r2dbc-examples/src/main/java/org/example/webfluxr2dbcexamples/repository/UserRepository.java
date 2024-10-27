package org.example.webfluxr2dbcexamples.repository;

import org.example.webfluxr2dbcexamples.dox.UserReact;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserReact, String> {

    Mono<UserReact> findByAccount(String account);

    @Query("""
            select * from user_react u where u.role=:role;
            """)
    Flux<UserReact> findByRole(String role);
}
