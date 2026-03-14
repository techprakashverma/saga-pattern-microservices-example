package com.gateway.ms.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkeymysecretkey";

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public Claims validateToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {

        return validateToken(token).getSubject();
    }

    public String extractRole(String token) {

        return validateToken(token).get("role", String.class);
    }
}