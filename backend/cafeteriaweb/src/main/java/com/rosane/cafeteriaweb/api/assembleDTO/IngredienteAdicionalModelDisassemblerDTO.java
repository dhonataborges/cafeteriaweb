package com.rosane.cafeteriaweb.api.assembleDTO;

import com.rosane.cafeteriaweb.api.modelDTO.inputDTO.IngredienteAdicionalInputDTO;
import com.rosane.cafeteriaweb.api.modelDTO.inputDTO.IngredienteBaseInputDTO;
import com.rosane.cafeteriaweb.domain.model.IngredienteAdicional;
import com.rosane.cafeteriaweb.domain.model.IngredienteBase;
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
