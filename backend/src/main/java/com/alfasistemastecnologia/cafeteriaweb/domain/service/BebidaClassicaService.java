package com.alfasistemastecnologia.cafeteriaweb.domain.service;

import com.alfasistemastecnologia.cafeteriaweb.domain.exception.BebidaClassicaNãoEncontradoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.EntidadeEmUsoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.BebidaClassica;
import com.alfasistemastecnologia.cafeteriaweb.domain.repository.BebidaClassicaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BebidaClassicaService {

    private static final String MSG_BEBIDA_EM_USO = "Bebida Clássica do código %d não pode ser removida, pois está em uso.";

    @Autowired
    private BebidaClassicaRepository bebidaClassicaRepository;

    @Transactional
    public BebidaClassica salvar(@Valid BebidaClassica bebida) {
        return bebidaClassicaRepository.save(bebida);
    }

    @Transactional
    public void excluir(Long bebidaId) {
        try {
            bebidaClassicaRepository.deleteById(bebidaId);
        } catch (EmptyResultDataAccessException e) {
            throw new BebidaClassicaNãoEncontradoException(bebidaId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_BEBIDA_EM_USO, bebidaId));
        }
    }

    public BebidaClassica buscarOuFalhar(Long bebidaId) {
        return bebidaClassicaRepository.findById(bebidaId)
                .orElseThrow(() -> new BebidaClassicaNãoEncontradoException(bebidaId));
    }
}
