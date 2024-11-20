package com.example.login.config.security;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// ログインした次の2回目のリクエストでは、トークンの中身の署名を検証するだけでなく、DBに問い合わせる事も場合には行う
// つまりDBに問い合わせる場合はサービスを呼び出す必要がある。
// 署名の検証を行う。JWTトークンの中身(ヘッダー、ペイロード、署名化データ)からチェックする。
public class JwtUtils {

    private final SecretKey key;
    private final long expirationMs;

    // コンストラクタで秘密鍵と有効期限を注入
    public JwtUtils(String secretKey, long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expirationMs = expirationMs;
    }

    // JWTトークン生成
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // JWTトークンからクレーム部分を取得
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)  
                .build()
                // verifyWith(key) で設定した鍵で、トークンの署名部分と、ヘッダー＋ペイロードから計算された署名を比較
                .parseSignedClaims(token)
                .getPayload();
    }
}
