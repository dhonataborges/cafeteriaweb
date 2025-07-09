package com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UsuarioInputDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
}
