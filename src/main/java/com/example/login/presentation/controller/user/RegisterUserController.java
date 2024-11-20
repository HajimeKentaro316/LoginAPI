package com.example.login.presentation.controller.user;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.application.dto.user.RegisterUserRequestData;
import com.example.login.application.dto.user.RegisterUserResponseData;
import com.example.login.application.service.user.UserLoginService;

@RestController
public class RegisterUserController {
    @Autowired
    private Logger logger;

    @Autowired
    UserLoginService userLoginService;

    @PostMapping("/api/register")
    public RegisterUserResponseData register(@RequestBody(required = false) RegisterUserRequestData registerUserRequestData) {
        logger.debug("registerコントローラー到達");
        RegisterUserResponseData response = userLoginService.registerUser(registerUserRequestData);
        return response;
    }
}
