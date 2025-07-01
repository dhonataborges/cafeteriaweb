package com.rosane.cafeteriaweb.api.assembleDTO;

import com.rosane.cafeteriaweb.api.modelDTO.BebidaModelDTO;
import com.rosane.cafeteriaweb.api.modelDTO.IngredienteAdicionalModelDTO;
import com.rosane.cafeteriaweb.api.modelDTO.IngredienteBaseModelDTO;
import com.rosane.cafeteriaweb.domain.model.Bebida;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BebidaModelAssemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    public BebidaModelDTO toModel(Bebida bebida) {

        // Mapeia os dados básicos com ModelMapper
        BebidaModelDTO bebidaDTO = modelMapper.map(bebida, BebidaModelDTO.class);

        // Faz mapeamento manual da lista de ingredientes base
        List<IngredienteBaseModelDTO> baseDTO = bebida.getIngredientesBase().stream()
                .map(ingredienteBase -> {
                    IngredienteBaseModelDTO dtoBase = new IngredienteBaseModelDTO();
                    dtoBase.setId(ingredienteBase.getId());
                    dtoBase.setNome(ingredienteBase.getNome());
                    return dtoBase;
                })
                .collect(Collectors.toList());

        // Faz mapeamento manual da lista de ingredientes adicionais
        List<IngredienteAdicionalModelDTO> adicionalDTO = bebida.getIngredientesAdicional().stream()
                .map(ingredienteAdicional -> {
                    IngredienteAdicionalModelDTO dtoAdicional = new IngredienteAdicionalModelDTO();
                    dtoAdicional.setId(ingredienteAdicional.getId());
                    dtoAdicional.setNome(ingredienteAdicional.getNome());
                    return dtoAdicional;
                })
                .collect(Collectors.toList());

        bebidaDTO.setIngredienteBase(baseDTO);
        bebidaDTO.setIngredienteAdicional(adicionalDTO);

        return bebidaDTO;
    }

    public List<BebidaModelDTO> toCollectionModel(List<Bebida> bebidas) {
        return bebidas.stream()
                .map(bebida -> toModel(bebida))
                .collect(Collectors.toList());
    }
}
