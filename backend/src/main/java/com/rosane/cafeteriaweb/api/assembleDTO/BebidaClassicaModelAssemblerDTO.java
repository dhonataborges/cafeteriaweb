package com.rosane.cafeteriaweb.api.assembleDTO;

import com.rosane.cafeteriaweb.api.modelDTO.BebidaClassicaModelDTO;
import com.rosane.cafeteriaweb.api.modelDTO.BebidaModelDTO;
import com.rosane.cafeteriaweb.api.modelDTO.IngredienteAdicionalModelDTO;
import com.rosane.cafeteriaweb.api.modelDTO.IngredienteBaseModelDTO;
import com.rosane.cafeteriaweb.domain.model.Bebida;
import com.rosane.cafeteriaweb.domain.model.BebidaClassica;
import com.rosane.cafeteriaweb.domain.model.IngredienteBase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BebidaClassicaModelAssemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    public BebidaClassicaModelDTO toModel(BebidaClassica bebida) {
        // Mapeia os dados básicos com ModelMapper
        BebidaClassicaModelDTO bebidaClassicaDTO = modelMapper.map(bebida, BebidaClassicaModelDTO.class);

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

        bebidaClassicaDTO.setIngredienteBase(baseDTO);
        bebidaClassicaDTO.setIngredienteAdicional(adicionalDTO);

        return bebidaClassicaDTO;
    }

    public List<BebidaClassicaModelDTO> toCollectionModel(List<BebidaClassica> bebidas) {
        return bebidas.stream()
                .map(bebida -> toModel(bebida))
                .collect(Collectors.toList());
    }
}
