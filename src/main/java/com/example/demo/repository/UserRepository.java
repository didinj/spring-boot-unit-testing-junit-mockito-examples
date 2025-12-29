package com.example.demo.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository {
    Optional<User> findById(Long id);

    User save(User user);

    boolean existsByEmail(String email);

    User update(Long id, User user);

    void delete(User user);
}
