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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<TokenModelDTO> autenticar(@RequestBody @Valid LoginInputDTO loginDTO) {
        try {
            // 1. Tenta autenticar o usuário
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getSenha()
                    )
            );

            // 2. Busca o usuário no banco
            Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

            // 3. Gera o token com claims personalizadas
            String token = jwtTokenProvider.gerarToken(usuario);

            // 4. Retorna o token para o frontend
            return ResponseEntity.ok(new TokenModelDTO(token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
