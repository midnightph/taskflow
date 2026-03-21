package com.github.midnightph.taskflow.config;

import org.springframework.stereotype.Service;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    public SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) getSignInKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return Jwts.parser().verifyWith((SecretKey) getSignInKey()).build().parseSignedClaims(token).getPayload()
                .getSubject();
    }

}
