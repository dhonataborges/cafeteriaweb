package com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO;

import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.BebidaModelDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.IngredienteAdicionalModelDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.IngredienteBaseModelDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.UsuarioModelDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.Bebida;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioModelAssemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioModelDTO toModel(Usuario usuario) {

        return modelMapper.map(usuario, UsuarioModelDTO.class);
    }

    public List<UsuarioModelDTO> toCollectionModel(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> toModel(usuario))
                .collect(Collectors.toList());
    }
}
