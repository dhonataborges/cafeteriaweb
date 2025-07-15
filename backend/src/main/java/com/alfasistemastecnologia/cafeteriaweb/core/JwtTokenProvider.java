package com.alfasistemastecnologia.cafeteriaweb.core;

import com.alfasistemastecnologia.cafeteriaweb.domain.model.Usuario;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    ///private final Key chaveSecreta = Keys.secretKeyFor(SignatureAlgorithm.HS256); // chave gerada internamente

    @Value("${jwt.secret}")
    private String secret;

    private Key chaveSecreta;

    // Tempo de expiração: 1 hora (em milissegundos)
    private static final long EXPIRATION = 1000 * 60 * 60; // chave expira em 1h.

    @PostConstruct
    public void init() {
        chaveSecreta = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Gera um token para o usuário
    public String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("id", usuario.getId())
                .claim("nome", usuario.getNome())
                .claim("email", usuario.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(chaveSecreta, SignatureAlgorithm.HS256)
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
        } catch (ExpiredJwtException e) {
            System.out.println("Token expirado");
        } catch (SignatureException e) {
            System.out.println("Assinatura inválida");
        } catch (MalformedJwtException e) {
            System.out.println("Token malformado");
        } catch (Exception e) {
            System.out.println("Erro na validação do token: " + e.getMessage());
        }
        return false;
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