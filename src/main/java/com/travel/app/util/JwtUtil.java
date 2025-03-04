package com.travel.app.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "DipakZad@NESP2025&Travel_App2025";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims extractClaims(String token) {
    	Claims claims = Jwts.parser()
    	        .setSigningKey(SECRET_KEY)
    	        .build()
    	        .parseClaimsJws(token)
    	        .getBody();
        return claims;
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean ValidateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !extractClaims(token).getExpiration().before(new Date()));
    }
}

