package com.apiexample.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiexample.api.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
