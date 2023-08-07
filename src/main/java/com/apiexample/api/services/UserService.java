package com.apiexample.api.services;

import java.util.List;

import com.apiexample.api.domain.User;

public interface UserService {
    User findById(Integer id);

    List<User> findAll();
}
