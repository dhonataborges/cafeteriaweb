package com.alfasistemastecnologia.cafeteriaweb.core;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key chaveSecreta = Keys.secretKeyFor(SignatureAlgorithm.HS256); // chave gerada internamente

    // Tempo de expiração: 1 hora (em milissegundos)
    private static final long EXPIRATION = 1000 * 60 * 60;

    // Gera um token para o usuário
    public String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)                          // quem é o dono do token
                .setIssuedAt(new Date())                    // quando foi gerado
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)) // quando expira
                .signWith(chaveSecreta)                     // assinatura com chave secreta
                .compact();
    }

    // Verifica se o token é válido
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(chaveSecreta)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extrai o e-mail (subject) do token
    public String getEmailDoToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(chaveSecreta)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
