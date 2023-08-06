package com.apiexample.api.services;

import com.apiexample.api.domain.User;

public interface UserService {
    User findById(Integer id);
}
