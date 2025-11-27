package com.peterson.gerenciador_tarefas.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // CHAVE FIXA COM 64+ CARACTERES → sempre a mesma, mesmo reiniciando //PROVISÓRIO
    private static final SecretKey CHAVE = Keys.hmacShaKeyFor(
        "minhaChaveSuperSecretaDoPeterson2025QueNinguemVaiDescobrirNunca12345678901234567890".getBytes(StandardCharsets.UTF_8)
    );

    private static final long EXPIRACAO = 86400000L; // 24 horas

    public String gerarToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(CHAVE)
                .compact();
    }

    public String getEmail(String token) {
        return Jwts.parser()
                .verifyWith(CHAVE)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}