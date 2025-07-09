package com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BebidaClassicaInputDTO {
    private Long id;
    private String nome;
    private List<Long> ingredientesBase;
    private List<Long> ingredientesAdicional;

}
