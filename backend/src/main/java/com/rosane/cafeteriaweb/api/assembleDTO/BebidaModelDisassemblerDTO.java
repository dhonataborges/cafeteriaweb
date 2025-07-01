package com.rosane.cafeteriaweb.api.assembleDTO;

import com.rosane.cafeteriaweb.api.modelDTO.inputDTO.BebidaInputDTO;
import com.rosane.cafeteriaweb.domain.exception.NegocioException;
import com.rosane.cafeteriaweb.domain.model.Bebida;
import com.rosane.cafeteriaweb.domain.model.IngredienteAdicional;
import com.rosane.cafeteriaweb.domain.model.IngredienteBase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BebidaModelDisassemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    public Bebida toDomainObject(BebidaInputDTO bebidaDTO) {

        Bebida bebida = new Bebida(); // Cria uma nova instância da entidade Bebida

        // Mapeia os IDs dos ingredientes base (recebidos no DTO) para objetos IngredienteBase apenas com o ID
        // Não busca os dados completos do banco nesse momento (delega isso ao serviço posteriormente)
        List<IngredienteBase> bases = bebidaDTO.getIngredientesBase().stream()
                .map(id -> {
                    IngredienteBase ingrediente = new IngredienteBase();
                    ingrediente.setId(id);
                    return ingrediente;
                })
                .collect(Collectors.toList());

        // Validação: não permitir mais que 3 ingredientes base
        if (bases.size() > 3) {
            throw new NegocioException("A quantidade permitida de ingredientes base é de 3 no máximo!");
        }

        // Mapeia os IDs dos ingredientes adicionais (recebidos no DTO) para objetos IngredienteAdicional apenas com o ID
        List<IngredienteAdicional> adicionais = bebidaDTO.getIngredientesAdicional().stream()
                .map(id -> {
                    IngredienteAdicional ingrediente = new IngredienteAdicional();
                    ingrediente.setId(id);
                    return ingrediente;
                })
                .collect(Collectors.toList());

        // Validação: não permitir mais que 2 ingredientes adicionais
        if (adicionais.size() > 2) {
            throw new NegocioException("A quantidade permitida de ingredientes adicionais é de 2 no máximo!");
        }

        // Atribui as listas de ingredientes à entidade Bebida
        bebida.setIngredientesBase(bases);
        bebida.setIngredientesAdicional(adicionais);

        // Retorna a instância da entidade Bebida pronta para ser persistida
        return bebida;
    }

}
