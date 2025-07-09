package com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO;

import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.IngredienteAdicionalModelDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.IngredienteAdicional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngredienteAdicionalModelAssemblerDTO {
    @Autowired
    private ModelMapper modelMapper;

    public IngredienteAdicionalModelDTO toModel(IngredienteAdicional ingredienteAdicional) {
        return modelMapper.map(ingredienteAdicional, IngredienteAdicionalModelDTO.class);
    }

    public List<IngredienteAdicionalModelDTO> toCollectionModel(List<IngredienteAdicional> ingredienteAdicionals) {
        return ingredienteAdicionals.stream()
                .map(ingredienteAdicional -> toModel(ingredienteAdicional))
                .collect(Collectors.toList());
    }
}
