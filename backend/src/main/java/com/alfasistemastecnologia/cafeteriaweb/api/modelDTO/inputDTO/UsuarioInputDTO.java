package com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UsuarioInputDTO {
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "O email é obrigatório.")
    @Column(unique = true, name = "E-mail já está cadastrado!")
    @Email(message = "Informe um email válido.")
    private String email;

    private String senha;
}
