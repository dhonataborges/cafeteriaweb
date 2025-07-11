package com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginInputDTO {
    private Long id;
    private String email;
    private String senha;
}
