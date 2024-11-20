package com.example.login.application.dto.login;

import java.io.Serializable;
import com.example.login.domain.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseData implements Serializable{
    private User user;
    private Boolean isSuccess;
    private String errorMsg;
}
