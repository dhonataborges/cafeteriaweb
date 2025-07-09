package com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO;

import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.BebidaClassicaInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.BebidaClassica;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.IngredienteAdicional;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.IngredienteBase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BebidaClassicaModelDisassemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    public BebidaClassica toDomainObject(BebidaClassicaInputDTO bebidaClassicaDTO) {

        BebidaClassica bebida = new BebidaClassica();
        bebida.setNome(bebidaClassicaDTO.getNome());

        // Mapear os IDs para entidades IngredienteBase (somente com ID, sem buscar do banco)
        List<IngredienteBase> bases = bebidaClassicaDTO.getIngredientesBase().stream()
                .map(id -> {
                    IngredienteBase ingrediente = new IngredienteBase();
                    ingrediente.setId(id);
                    return ingrediente;
                })
                .collect(Collectors.toList());

        // Mapear os IDs para entidades IngredienteAdicional (somente com ID, sem buscar do banco)
        List<IngredienteAdicional> adicionais = bebidaClassicaDTO.getIngredientesAdicional().stream()
                .map(id -> {
                    IngredienteAdicional ingrediente = new IngredienteAdicional();
                    ingrediente.setId(id);
                    return ingrediente;
                })
                .collect(Collectors.toList());

        bebida.setIngredientesBase(bases);
        bebida.setIngredientesAdicional(adicionais);

        return bebida;
    }

}
