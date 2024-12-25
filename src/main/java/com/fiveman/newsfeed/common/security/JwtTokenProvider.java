package com.fiveman.newsfeed.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtTokenProvider {

    private final Key secretKey;
    private final long validatedInAnHour = 3600000;

    public JwtTokenProvider() {
        // 32바이트 이상의 문자열을 사용해 비밀 키 생성
        String keyString = "your-secure-key-must-be-32-characters-or-more";
        this.secretKey = Keys.hmacShaKeyFor(keyString.getBytes());
    }

    // 토큰 생성
    public String createToken(String email, Long userId) {

        return Jwts.builder()
                .setSubject(email) // 이메일을 Subject로 설정
                .claim("userId", userId) // 유저 ID 추가
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + validatedInAnHour)) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 서명
                .compact();
    }

    // 토큰에서 이메일 추출
    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // 비밀 키
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // username 추출
    }

    // 토큰에서 id 추출
    public Long getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class); // 클레임에서 유저 ID 추출
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // 비밀 키
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // 토큰 만료 여부 확인
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}