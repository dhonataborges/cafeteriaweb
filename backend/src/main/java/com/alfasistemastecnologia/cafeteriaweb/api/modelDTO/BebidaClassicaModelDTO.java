package com.alfasistemastecnologia.cafeteriaweb.api.modelDTO;

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
