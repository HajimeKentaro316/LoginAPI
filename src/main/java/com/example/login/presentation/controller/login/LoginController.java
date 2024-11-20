package com.example.login.presentation.controller.login;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.application.dto.login.LoginRequestData;
import com.example.login.application.dto.login.LoginResponseData;
import com.example.login.application.service.user.UserLoginService;
import com.example.login.config.security.JwtUtils;

@RestController
public class LoginController {
    @Autowired
    private Logger logger;

    private final UserLoginService userLoginService; // 認証サービス
    private final JwtUtils jwtUtils;       // JWT トークン生成ユーティリテ

    @Autowired
    public LoginController(UserLoginService userLoginService, JwtUtils jwtUtils) {
        this.userLoginService = userLoginService;
        this.jwtUtils = jwtUtils;
    }
    
    @PostMapping("/api/login")
    public ResponseEntity<LoginResponseData> login(@RequestBody(required = false) LoginRequestData loginRequestData) {
        logger.debug("loginコントローラー到達");
        LoginResponseData responseData = userLoginService.login(loginRequestData);
        if (responseData.getIsSuccess()) {
            // JWTトークンの生成(JWTUtilsを呼び出す)
            String token = jwtUtils.generateToken(loginRequestData.getEmail());
            // ヘッダーにトークンを設定
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            // トークンを付与したResponseEntityを返す
            return ResponseEntity.ok()
                    .headers(headers)       // トークンをヘッダーに追加
                    .body(responseData);    // レスポンスボディにデータを格納
        }
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseData);
    }
}
