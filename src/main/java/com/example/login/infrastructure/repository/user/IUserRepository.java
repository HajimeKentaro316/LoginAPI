package com.example.login.infrastructure.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.login.domain.model.user.User;
import com.example.login.domain.model.user.UserId;

public interface IUserRepository extends JpaRepository<User, UserId>{
    // JPAが自動的に以下のクエリを生成してくれる
    // SELECT * FROM users WHERE email = ? AND password = ?
    Optional<User> findByEmail(String email);
}
