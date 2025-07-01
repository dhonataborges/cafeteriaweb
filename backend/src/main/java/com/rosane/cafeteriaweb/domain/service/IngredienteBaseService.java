package com.rosane.cafeteriaweb.domain.service;

import com.rosane.cafeteriaweb.domain.exception.BebidaNãoEncontradoException;
import com.rosane.cafeteriaweb.domain.exception.EntidadeEmUsoException;
import com.rosane.cafeteriaweb.domain.exception.IngredienteBaseNãoEncontradoException;
import com.rosane.cafeteriaweb.domain.model.Bebida;
import com.rosane.cafeteriaweb.domain.model.IngredienteBase;
import com.rosane.cafeteriaweb.domain.repository.BebidaRepository;
import com.rosane.cafeteriaweb.domain.repository.IngredienteBaseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredienteBaseService {

    private static final String MSG_INGREDIENTE_BASE_EM_USO = "Ingredientes base do código %d não pode ser removida, pois está em uso.";

    @Autowired
    private IngredienteBaseRepository ingredienteBaseRepository;

    @Transactional
    public IngredienteBase salvar(@Valid IngredienteBase ingredienteBase) {

        return ingredienteBaseRepository.save(ingredienteBase);
    }

    @Transactional
    public void excluir(Long ingredienteBaseId) {
        try {
            ingredienteBaseRepository.deleteById(ingredienteBaseId);
        } catch (EmptyResultDataAccessException e) {
            throw new IngredienteBaseNãoEncontradoException(ingredienteBaseId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_INGREDIENTE_BASE_EM_USO, ingredienteBaseId));
        }
    }

    public IngredienteBase buscarOuFalhar(Long ingredienteBaseId) {
        return ingredienteBaseRepository.findById(ingredienteBaseId)
                .orElseThrow(() -> new IngredienteBaseNãoEncontradoException(ingredienteBaseId));
    }
}
