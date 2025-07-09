package com.alfasistemastecnologia.cafeteriaweb.api.modelDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BebidaModelDTO {
    private Long id;
    private List<IngredienteBaseModelDTO> ingredienteBase;
    private List<IngredienteAdicionalModelDTO> ingredienteAdicional;
    private String bebidaCriada;
}
