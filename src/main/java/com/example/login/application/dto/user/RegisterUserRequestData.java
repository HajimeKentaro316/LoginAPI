package com.example.login.application.dto.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequestData implements Serializable{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
