package com.suman.notedown.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 15*60*1000)) // 15 minutes
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getUrlDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractSubject(Claims claims) {
        return claims.getSubject();
    }

    private Date extractExpiration(Claims claims) {
        return claims.getExpiration();
    }

    private boolean isTokenExpired(Claims claims) {
        return extractExpiration(claims).before(new Date());
    }

    public boolean isTokenValid(Claims claims) {
        return !isTokenExpired(claims);
    }

}
