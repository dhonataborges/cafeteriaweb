package com.rosane.cafeteriaweb.api.modelDTO.inputDTO;

import com.rosane.cafeteriaweb.domain.model.IngredienteAdicional;
import com.rosane.cafeteriaweb.domain.model.IngredienteBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BebidaInputDTO {
    private Long id;
    private List<Long> ingredientesBase;
    private List<Long> ingredientesAdicional;
    private String bebidaCriada;
}
