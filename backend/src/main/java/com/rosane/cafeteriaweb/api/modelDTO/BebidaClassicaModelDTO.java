package com.rosane.cafeteriaweb.api.modelDTO;

import com.rosane.cafeteriaweb.domain.model.IngredienteAdicional;
import com.rosane.cafeteriaweb.domain.model.IngredienteBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BebidaClassicaModelDTO {
    private Long id;
    private String nome;
    private List<IngredienteBaseModelDTO> ingredienteBase;
    private List<IngredienteAdicionalModelDTO> ingredienteAdicional;
}
