package com.alfasistemastecnologia.cafeteriaweb.api.controller;

import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.UsuarioModelAssemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.UsuarioModelDisassemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.TokenModelDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.UsuarioModelDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.LoginInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.UsuarioInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.core.JwtTokenProvider;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.NegocioException;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.UsuarioNãoEncontradoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.Usuario;
import com.alfasistemastecnologia.cafeteriaweb.domain.repository.UsuarioRepository;
import com.alfasistemastecnologia.cafeteriaweb.domain.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<TokenModelDTO> autenticar(@RequestBody LoginInputDTO loginDTO) {
        try {
            // Tenta autenticar o usuário com email e senha
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getSenha()
                    )
            );

            // Se autenticou, gera o token JWT
            String token = jwtTokenProvider.gerarToken(loginDTO.getEmail());

            return ResponseEntity.ok(new TokenModelDTO(token));
        } catch (AuthenticationException ex) {
            // Se falhar, retorna 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
