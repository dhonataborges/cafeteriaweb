package com.alfasistemastecnologia.cafeteriaweb.domain.service;

import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.BebidaModelDisassemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.BebidaInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.BebidaNãoEncontradoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.EntidadeEmUsoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.NegocioException;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.UsuarioNãoEncontradoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.*;
import com.alfasistemastecnologia.cafeteriaweb.domain.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Serviço responsável por gerenciar usuarios.
 * Contém lógica para salvar, atualizar, excluir e buscar usuarios com validações.
 */
@Service
public class UsuarioService {

    private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removido, pois está em uso.";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Salva um novo usuario no banco, codificando a senha e validando unicidade do email.
     */
    @Transactional
    public Usuario salvar(@Valid Usuario usuario) {

        // Verifica se o e-mail já está em uso
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new NegocioException("E-mail já está em uso.");
        }

        // Garante que a senha foi informada
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new NegocioException("Senha obrigatória no cadastro.");
        }

        // Bloqueia envio de senha já criptografada
        if (usuario.getSenha().startsWith("$2a$") || usuario.getSenha().startsWith("$2b$")) {
            throw new NegocioException("Senha não pode estar criptografada. Envie em texto puro.");
        }

        // Codifica a senha antes de persistir
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return usuarioRepository.save(usuario);
    }

    /**
     * Atualiza um usuario existente com validações de unicidade e codificação de senha.
     */
    @Transactional
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (!usuarioAtualizado.getEmail().equals(existente.getEmail()) &&
                usuarioRepository.findByEmail(usuarioAtualizado.getEmail()).isPresent()) {
            throw new NegocioException("E-mail já está em uso.");
        }

        existente.setNome(usuarioAtualizado.getNome());
        existente.setEmail(usuarioAtualizado.getEmail());

        String novaSenha = usuarioAtualizado.getSenha();
        if (novaSenha != null && !novaSenha.trim().isEmpty()) {
            if (novaSenha.startsWith("$2a$") || novaSenha.startsWith("$2b$")) {
                throw new NegocioException("Senha já criptografada. Envie em texto puro.");
            }
            existente.setSenha(passwordEncoder.encode(novaSenha));
        }

        return usuarioRepository.save(existente);
    }

    /**
     * Exclui um usuario pelo ID, tratando erros caso não exista ou esteja em uso.
     */
    @Transactional
    public void excluir(Long usuarioId) {
        try {
            usuarioRepository.deleteById(usuarioId);
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNãoEncontradoException(usuarioId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, usuarioId));
        }
    }

    /**
     * Busca um usuario pelo ID ou lança exceção caso não seja encontrado.
     */
    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNãoEncontradoException(usuarioId));
    }
}