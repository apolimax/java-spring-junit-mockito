package com.apiexample.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.apiexample.api.domain.User;
import com.apiexample.api.repositories.UserRepository;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    UserRepository repository;

    @Bean
    public void startDB() {
        User u1 = new User(null, "João", "jo@gmail.com", "123");
        User u2 = new User(null, "José", "jos@gmail.com", "123");

        repository.saveAll(Arrays.asList(u1, u2));
    }
}
