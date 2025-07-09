package com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO;

import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.BebidaInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.UsuarioInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.NegocioException;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.Bebida;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.IngredienteAdicional;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.IngredienteBase;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioModelDisassemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioInputDTO usuarioInputDTO) {

        return modelMapper.map(usuarioInputDTO, Usuario.class);
    }
}
