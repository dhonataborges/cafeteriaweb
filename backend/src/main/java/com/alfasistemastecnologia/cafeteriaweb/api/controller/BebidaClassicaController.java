package com.alfasistemastecnologia.cafeteriaweb.api.controller;

import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.BebidaClassicaInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.BebidaClassicaModelAssemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.BebidaClassicaModelDisassemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.BebidaClassicaModelDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.BebidaClassicaNãoEncontradoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.NegocioException;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.BebidaClassica;
import com.alfasistemastecnologia.cafeteriaweb.domain.repository.BebidaClassicaRepository;
import com.alfasistemastecnologia.cafeteriaweb.domain.service.BebidaClassicaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/bebida-classica")
public class BebidaClassicaController {

    @Autowired
    private BebidaClassicaService bebidaService;

    @Autowired
    private BebidaClassicaRepository bebidaRepository;

    @Autowired
    private BebidaClassicaModelAssemblerDTO bebidaModelAssemblerDTO;

    @Autowired
    private BebidaClassicaModelDisassemblerDTO bebidaModelDisassemblerDTO;

    @GetMapping
    public List<BebidaClassicaModelDTO> listar() {
        return bebidaModelAssemblerDTO.toCollectionModel(bebidaRepository.findAll());
    }

    @GetMapping("/{bebidaClassicaId}")
    public BebidaClassicaModelDTO buscar(@PathVariable Long bebidaClassicaId) {
        BebidaClassica bebida = bebidaService.buscarOuFalhar(bebidaClassicaId);
        return bebidaModelAssemblerDTO.toModel(bebida);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public BebidaClassicaModelDTO adicionar(@RequestBody @Valid BebidaClassicaInputDTO bebidaInputDTO) {
        try {
            BebidaClassica bebida = bebidaModelDisassemblerDTO.toDomainObject(bebidaInputDTO);
            return bebidaModelAssemblerDTO.toModel(bebidaService.salvar(bebida));
        } catch (BebidaClassicaNãoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{bebidaClassicaId}")
    public BebidaClassicaModelDTO atualizar(@PathVariable Long bebidaClassicaId, @RequestBody @Valid BebidaClassicaInputDTO bebidaInputDTO) {
        try {
            BebidaClassica bebida = bebidaModelDisassemblerDTO.toDomainObject(bebidaInputDTO);
            BebidaClassica bebidaAtual = bebidaService.buscarOuFalhar(bebidaClassicaId);
            BeanUtils.copyProperties(bebida, bebidaAtual, "id");
            return bebidaModelAssemblerDTO.toModel(bebidaService.salvar(bebida));
        } catch (BebidaClassicaNãoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{bebidaClassicaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long bebidaClassicaId) {
        BebidaClassica bebida = bebidaService.buscarOuFalhar(bebidaClassicaId);
        bebidaService.excluir(bebidaClassicaId);
    }

}