package com.example.login.application.dto.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationData implements Serializable{
    private String email;
    private Boolean isSuccess;
    private String message;
}