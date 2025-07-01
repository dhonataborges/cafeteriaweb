package com.rosane.cafeteriaweb.api;

import com.rosane.cafeteriaweb.api.assembleDTO.BebidaModelDisassemblerDTO;
import com.rosane.cafeteriaweb.api.modelDTO.inputDTO.BebidaInputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a conversão de BebidaInputDTO em Bebida (entidade de domínio).
 * Testa as regras de negócio relacionadas à quantidade máxima de ingredientes permitidos.
 */
class BebidaDisassemblerTests {

    private BebidaModelDisassemblerDTO disassembler;

    @BeforeEach
    void setUp() {
        // Inicializa o objeto responsável por converter o DTO em entidade
        disassembler = new BebidaModelDisassemblerDTO();
    }

    @Test
    void toDomainObject_deveLancarExcecaoSeMaisDe3IngredientesBase() {
        // Cria um DTO com 4 ingredientes base (excede o limite permitido de 3)
        BebidaInputDTO dto = new BebidaInputDTO();
        dto.setIngredientesBase(Arrays.asList(1L, 2L, 3L, 4L)); // inválido
        dto.setIngredientesAdicional(List.of(5L)); // válido

        // Verifica se a exceção correta é lançada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            disassembler.toDomainObject(dto);
        });

        // Valida a mensagem da exceção
        assertEquals("A quantidade permitida de ingredientes base é de 3 no máximo!", exception.getMessage());
    }

    @Test
    void toDomainObject_deveLancarExcecaoSeMaisDe2IngredientesAdicionais() {
        // Cria um DTO com 3 ingredientes adicionais (excede o limite permitido de 2)
        BebidaInputDTO dto = new BebidaInputDTO();
        dto.setIngredientesBase(List.of(1L)); // válido
        dto.setIngredientesAdicional(Arrays.asList(5L, 6L, 7L)); // inválido

        // Verifica se a exceção correta é lançada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            disassembler.toDomainObject(dto);
        });

        // Valida a mensagem da exceção
        assertEquals("A quantidade permitida de ingredientes adicionais é de 2 no máximo!", exception.getMessage());
    }

    @Test
    void toDomainObject_deveConverterComSucessoSeQuantidadesValidas() {
        // Cria um DTO com quantidades válidas de ingredientes
        BebidaInputDTO dto = new BebidaInputDTO();
        dto.setIngredientesBase(Arrays.asList(1L, 2L)); // válido
        dto.setIngredientesAdicional(List.of(3L));      // válido

        // Verifica se nenhum erro é lançado na conversão
        assertDoesNotThrow(() -> disassembler.toDomainObject(dto));
    }
}