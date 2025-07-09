package com.alfasistemastecnologia.cafeteriaweb.domain.service;

import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.BebidaModelDisassemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.BebidaInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.BebidaNãoEncontradoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.EntidadeEmUsoException;
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
 * Serviço responsável por gerenciar bebidas (clássicas e personalizadas).
 * Contém lógica de verificação de ingredientes, persistência e exclusão segura.
 */
@Service
public class UsuarioService {

    private static final String MSG_USUARIO_EM_USO = "Bebida do código %d não pode ser removida, pois está em uso.";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Salva uma bebida no banco de dados, associando corretamente os ingredientes,
     * e verificando se ela corresponde a uma bebida clássica conhecida.
     */
    @Transactional
    public Usuario salvar(@Valid Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, @Valid Usuario usuarioAtualizado) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // Atualiza os dados básicos
        existente.setNome(usuarioAtualizado.getNome());
        existente.setEmail(usuarioAtualizado.getEmail());

        // Atualiza a senha SOMENTE se ela tiver sido alterada
        String novaSenha = usuarioAtualizado.getSenha();

        if (novaSenha != null && !novaSenha.isBlank()) {
            // Se a nova senha estiver diferente da atual (desconsiderando hash)
            if (!passwordEncoder.matches(novaSenha, existente.getSenha())) {
                existente.setSenha(passwordEncoder.encode(novaSenha));
            }
        }

        return usuarioRepository.save(existente);
    }

    /**
     * Exclui uma bebida pelo ID. Lança exceções customizadas se não encontrada ou se estiver em uso.
     */
    @Transactional
    public void excluir(Long usuarioId) {
        try {
            usuarioRepository.deleteById(usuarioId);
        } catch (EmptyResultDataAccessException e) {
            // Se o ID não existir
            throw new BebidaNãoEncontradoException(usuarioId);
        } catch (DataIntegrityViolationException e) {
            // Se a bebida estiver sendo usada em outra tabela (chave estrangeira, etc)
            throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, usuarioId));
        }
    }

    /**
     * Retorna uma bebida pelo ID ou lança exceção se não existir.
     */
    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNãoEncontradoException(usuarioId));
    }

}