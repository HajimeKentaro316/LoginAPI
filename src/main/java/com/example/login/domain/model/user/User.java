package com.example.login.domain.model.user;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @EmbeddedId
    private UserId userId;

    @Embedded
    private UserName userName;

    private String email;
    private String password;

    @Column(name ="created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{8,}$");

    public User() {}

    // 会員登録用
    public User(UserName userName, String email, String password) {
        // id(識別子)を生成
        this.userId = new UserId();

        // 名前のセット
        this.userName = userName;

        // メールアドレスのチェック
        if (!EMAIL_PATTERN.matcher(email).matches()) 
            throw new IllegalArgumentException("メールアドレスが不正です。：" + email);
        this.email = email;

        // パスワードのチェック
        if (!PASSWORD_PATTERN.matcher(password).matches())
            throw new IllegalArgumentException("パスワードが不正です。：" + password);
        this.password = password;

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public UserName getUserName() {
        return userName;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEncodePassword(String password) {
        this.password = password;
    }
}
