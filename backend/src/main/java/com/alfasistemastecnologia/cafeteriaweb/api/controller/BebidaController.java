package com.alfasistemastecnologia.cafeteriaweb.api.controller;

import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.BebidaModelDisassemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.BebidaInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.BebidaModelAssemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.BebidaModelDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.BebidaNãoEncontradoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.NegocioException;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.Bebida;
import com.alfasistemastecnologia.cafeteriaweb.domain.repository.BebidaRepository;
import com.alfasistemastecnologia.cafeteriaweb.domain.service.BebidaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/bebida")
public class BebidaController {

    @Autowired
    private BebidaService bebidaService;

    @Autowired
    private BebidaRepository bebidaRepository;

    @Autowired
    private BebidaModelAssemblerDTO bebidaModelAssemblerDTO;

    @Autowired
    private BebidaModelDisassemblerDTO bebidaModelDisassemblerDTO;

    @GetMapping
    public List<BebidaModelDTO> listar() {
        return bebidaModelAssemblerDTO.toCollectionModel(bebidaRepository.findAll());
    }

    @GetMapping("/{bebidaId}")
    public BebidaModelDTO buscar(@PathVariable Long bebidaId) {
        Bebida bebida = bebidaService.buscarOuFalhar(bebidaId);
        return bebidaModelAssemblerDTO.toModel(bebida);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public BebidaModelDTO adicionar(@RequestBody @Valid BebidaInputDTO bebidaInputDTO) {
        try {
            Bebida bebida = bebidaModelDisassemblerDTO.toDomainObject(bebidaInputDTO);
            return bebidaModelAssemblerDTO.toModel(bebidaService.salvar(bebida));
        } catch (BebidaNãoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PostMapping("/verificar")
    public BebidaModelDTO verificarBebida(@RequestBody @Valid BebidaInputDTO bebidaInputDTO) {
        Bebida bebida = bebidaService.montarBebidaComNomeClassicoOuPersonalizado(bebidaInputDTO);
        return bebidaModelAssemblerDTO.toModel(bebida);
    }

    @PutMapping("/{bebidaId}")
    public BebidaModelDTO atualizar(@PathVariable Long bebidaId, @RequestBody @Valid BebidaInputDTO bebidaInputDTO) {
        try {
            Bebida bebida = bebidaModelDisassemblerDTO.toDomainObject(bebidaInputDTO);
            Bebida bebidaAtual = bebidaService.buscarOuFalhar(bebidaId);
            BeanUtils.copyProperties(bebida, bebidaAtual, "id");
            return bebidaModelAssemblerDTO.toModel(bebidaService.salvar(bebida));
        } catch (BebidaNãoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{bebidaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long bebidaId) {
        Bebida bebida = bebidaService.buscarOuFalhar(bebidaId);
        bebidaService.excluir(bebidaId);
    }

}