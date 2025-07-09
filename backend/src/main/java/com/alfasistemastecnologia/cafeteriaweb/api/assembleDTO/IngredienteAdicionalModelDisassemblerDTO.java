package com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO;

import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.IngredienteAdicionalInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.IngredienteAdicional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IngredienteAdicionalModelDisassemblerDTO {
    @Autowired
    private ModelMapper modelMapper;

    public IngredienteAdicional toDomainObject(IngredienteAdicionalInputDTO ingredienteAdicionalInputDTO) {
        return modelMapper.map(ingredienteAdicionalInputDTO, IngredienteAdicional.class);
    }
}
