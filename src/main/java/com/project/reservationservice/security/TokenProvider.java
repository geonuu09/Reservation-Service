package com.project.reservationservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider {
    // 상수 값들은 그대로 유지
    private static final long TOKEN_EXPIRATION_TIME = 60 * 60 * 1000; // 1 시간
    private static final String KEY_ROLES = "roles";

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private Key key;

    // @PostConstruct를 사용하여 빈 초기화 시 키를 생성
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        log.info("JWT 비밀 키가 초기화되었습니다.");
    }

    public String createToken(String email, Collection<? extends GrantedAuthority> authorities) {
        log.debug("JWT 토큰 생성 시도: {}", email);

        Claims claims = Jwts.claims().setSubject(email);
        claims.put(KEY_ROLES, authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);

        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(expiredDate)
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();

            log.debug("JWT 토큰 생성 성공: {}", email);
            return token;
        } catch (Exception e) {
            log.error("JWT 토큰 생성 실패: {}", email, e);
            throw new RuntimeException("JWT 토큰 생성 중 오류 발생", e);
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            log.error("토큰에서 사용자 이름을 추출하는 데 실패했습니다.", e);
            throw new RuntimeException("Invalid token", e);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.", e);
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.", e);
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.", e);
        }
        return false;
    }
}