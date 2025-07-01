package com.rosane.cafeteriaweb.api.assembleDTO;

import com.rosane.cafeteriaweb.api.modelDTO.IngredienteAdicionalModelDTO;
import com.rosane.cafeteriaweb.api.modelDTO.IngredienteBaseModelDTO;
import com.rosane.cafeteriaweb.domain.model.IngredienteAdicional;
import com.rosane.cafeteriaweb.domain.model.IngredienteBase;
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
