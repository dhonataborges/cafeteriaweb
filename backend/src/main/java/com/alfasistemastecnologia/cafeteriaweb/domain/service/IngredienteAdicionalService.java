package com.alfasistemastecnologia.cafeteriaweb.domain.service;

import com.alfasistemastecnologia.cafeteriaweb.domain.model.IngredienteAdicional;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.EntidadeEmUsoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.IngredienteAdicionalNãoEncontradoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.repository.IngredienteAdicionalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredienteAdicionalService {

    private static final String MSG_INGREDIENTE_ADICIONAL_EM_USO = "Ingrediente adicional do código %d não pode ser removida, pois está em uso.";

    @Autowired
    private IngredienteAdicionalRepository ingredienteAdicionalRepository;

    @Transactional
    public IngredienteAdicional salvar(@Valid IngredienteAdicional ingredienteAdicional) {

        return ingredienteAdicionalRepository.save(ingredienteAdicional);
    }

    @Transactional
    public void excluir(Long ingredienteAdicionalId) {
        try {
            ingredienteAdicionalRepository.deleteById(ingredienteAdicionalId);
        } catch (EmptyResultDataAccessException e) {
            throw new IngredienteAdicionalNãoEncontradoException(ingredienteAdicionalId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_INGREDIENTE_ADICIONAL_EM_USO, ingredienteAdicionalId));
        }
    }

    public IngredienteAdicional buscarOuFalhar(Long ingredienteAdicionalId) {
        return ingredienteAdicionalRepository.findById(ingredienteAdicionalId)
                .orElseThrow(() -> new IngredienteAdicionalNãoEncontradoException(ingredienteAdicionalId));
    }
}
