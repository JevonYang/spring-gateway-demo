package com.yang.gateway.service;

import com.yang.gateway.model.User;
import com.yang.gateway.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<User> save(User user) {
        return userRepository.save(user)
                .onErrorResume(e ->
                        userRepository.findUserByUsername(user.getUsername())
                                .flatMap(oUser -> {
                                    user.setId(oUser.getId());
                                    return userRepository.save(user);
                                }));
    }

    public Mono<Long> deleteByUsername(String username) {
        return userRepository.deleteUserByUsername(username);
    }

    public Mono<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

}
