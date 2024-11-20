package com.example.login.application.service.authenticate;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.login.infrastructure.repository.user.IUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private Logger logger;

    private final IUserRepository userRepository;

    // コンストラクタでBCryptPasswordEncoderとIUserRepositoryを注入
    @Autowired
    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ユーザー名に基づいてユーザーをデータベースなどから取得する処理を書く
        Optional<com.example.login.domain.model.user.User> oldUser = userRepository.findByEmail(username);
        if (oldUser.isPresent()) {
            return User.builder()
            .username(username) // メールアドレスのセット
            .password(oldUser.get().getPassword())
            .roles("USER")
            .build();
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}
