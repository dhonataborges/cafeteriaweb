package com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginInputDTO {
    private Long id;

    @Column(unique = true, name = "E-mail já está cadastrado!")
    @Email(message = "Informe um email válido.")
    private String email;
    private String senha;

}
