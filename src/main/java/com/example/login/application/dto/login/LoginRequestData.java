package com.example.login.application.dto.login;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestData implements Serializable{
    private String email;
    private String password;

    public LoginRequestData(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
