package com.yang.gateway.dao;

import com.yang.gateway.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String> {

    Mono<User> findUserByUsername(String username);
    Mono<Long> deleteUserByUsername(String username);

}
