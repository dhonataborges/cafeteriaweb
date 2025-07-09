package com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO;

import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.IngredienteBaseInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.IngredienteBase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IngredienteBaseModelDisassemblerDTO {
    @Autowired
    private ModelMapper modelMapper;

    public IngredienteBase toDomainObject(IngredienteBaseInputDTO ingredienteBaseInputDTO) {
        return modelMapper.map(ingredienteBaseInputDTO, IngredienteBase.class);
    }
}
