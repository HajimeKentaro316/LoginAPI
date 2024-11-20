package com.example.login.config.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private Logger logger;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.debug("doFilterInternalメソッド到達");
        try {
            // Authorizationヘッダーからトークンを取得
            String token = extractToken(request);
            if (token != null) {
                logger.debug("Tokenを検知しました");
                authenticateToken(token); // 認証を実行
            }
            logger.debug("Token==nullでした");
        } catch (Exception e) {
            // 例外をログ出力し、後続のフィルタ処理は継続
            logger.warn("JWTトークンの検証中にエラーが発生しました: {}", e.getMessage(), e);
        }
        // 次のフィルタを呼び出し
        filterChain.doFilter(request, response);
    }


    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            logger.debug("Authorizationヘッダーを検出しました");
            return header.substring(TOKEN_PREFIX.length()); // "Bearer "を取り除く
        }
        return null;
    }

    private void authenticateToken(String token) {
        // SecurityContextに認証情報がセットされている場合はスキップ
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            logger.debug("既に認証情報がセットされています。認証処理をスキップします。");
            return;
        }
        
        try {
            // トークンからクレームを取得（署名の検証含む）
            Claims claims = jwtUtils.parseToken(token);
            logger.debug("署名は改ざんされていません");
            System.out.println("claims:" + claims);
            logger.debug(claims.getSubject());
            String email = claims.getSubject();
            
            if (email != null) {
                logger.debug("トークンからメールアドレスを取り出しました！。メールアドレス：" + email);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                logger.debug("トークンからメールアドレスを取り出しました。メールアドレス：" + email);

                // 有効期限の検証
                if (!isTokenExpired(claims)) {
                    Authentication authentication = getAuthentication(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.debug("JWTトークンによる認証が成功しました: {}", email);
                } else {
                    logger.warn("JWTトークンが期限切れです: {}", token);
                }
            }
        } catch (Exception e) {
            logger.warn("JWTトークンの処理中にエラーが発生しました: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    // トークンの有効期限が切れているか確認
    private boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    private Authentication getAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }
}
