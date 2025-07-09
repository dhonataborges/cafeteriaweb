package com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO;

import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.IngredienteBaseModelDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.IngredienteBase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngredienteBaseModelAssemblerDTO {
    @Autowired
    private ModelMapper modelMapper;

    public IngredienteBaseModelDTO toModel(IngredienteBase ingredienteBase) {
        return modelMapper.map(ingredienteBase, IngredienteBaseModelDTO.class);
    }

    public List<IngredienteBaseModelDTO> toCollectionModel(List<IngredienteBase> ingredienteBases) {
        return ingredienteBases.stream()
                .map(ingredienteBase -> toModel(ingredienteBase))
                .collect(Collectors.toList());
    }
}
