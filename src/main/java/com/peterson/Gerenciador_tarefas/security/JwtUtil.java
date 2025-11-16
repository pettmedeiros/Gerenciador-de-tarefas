package com.peterson.gerenciador_tarefas.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    private final String CHAVE = "minhaChaveSecreta123456";
    private final long EXPIRACAO = 86400000L; // 24 horas

    // GERA O TOKEN
    public String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(SignatureAlgorithm.HS512, CHAVE)
                .compact();
    }

    // EXTRAI O EMAIL DO TOKEN
    public String getEmail(String token) {
        return Jwts.parser()
                .setSigningKey(CHAVE)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}