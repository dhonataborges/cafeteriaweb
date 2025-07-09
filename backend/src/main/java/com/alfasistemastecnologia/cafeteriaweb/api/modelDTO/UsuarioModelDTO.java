package com.alfasistemastecnologia.cafeteriaweb.api.modelDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UsuarioModelDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
}
