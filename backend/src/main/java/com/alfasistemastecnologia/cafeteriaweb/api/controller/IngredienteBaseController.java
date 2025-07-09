package com.alfasistemastecnologia.cafeteriaweb.api.controller;

import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.IngredienteBaseModelAssemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.IngredienteBaseModelDisassemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.IngredienteBaseModelDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.IngredienteBaseInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.IngredienteBaseNãoEncontradoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.NegocioException;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.IngredienteBase;
import com.alfasistemastecnologia.cafeteriaweb.domain.repository.IngredienteBaseRepository;
import com.alfasistemastecnologia.cafeteriaweb.domain.service.IngredienteBaseService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/ingrediente-base")
public class IngredienteBaseController {

    @Autowired
    private IngredienteBaseService ingredienteBaseService;

    @Autowired
    private IngredienteBaseRepository ingredienteBaseRepository;

    @Autowired
    private IngredienteBaseModelAssemblerDTO ingredienteBaseModelAssemblerDTO;

    @Autowired
    private IngredienteBaseModelDisassemblerDTO ingredienteBaseModelDisassemblerDTO;

    @GetMapping
    public List<IngredienteBaseModelDTO> listar() {
        return ingredienteBaseModelAssemblerDTO.toCollectionModel(ingredienteBaseRepository.findAll());
    }

    @GetMapping("/{baseId}")
    public IngredienteBaseModelDTO buscar(@PathVariable Long baseId) {
        IngredienteBase ingredienteBase = ingredienteBaseService.buscarOuFalhar(baseId);
        return ingredienteBaseModelAssemblerDTO.toModel(ingredienteBase);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public IngredienteBaseModelDTO adicionar(@RequestBody @Valid IngredienteBaseInputDTO ingredienteBaseInputDTO) {
        try {
            IngredienteBase ingredienteBase = ingredienteBaseModelDisassemblerDTO.toDomainObject(ingredienteBaseInputDTO);
            return ingredienteBaseModelAssemblerDTO.toModel(ingredienteBaseService.salvar(ingredienteBase));
        } catch (IngredienteBaseNãoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{baseId}")
    public IngredienteBaseModelDTO atualizar(@PathVariable Long baseId, @RequestBody @Valid IngredienteBaseInputDTO ingredienteBaseInputDTO) {
        try {
            IngredienteBase ingredienteBase = ingredienteBaseModelDisassemblerDTO.toDomainObject(ingredienteBaseInputDTO);
            IngredienteBase ingredienteBaseAtual = ingredienteBaseService.buscarOuFalhar(baseId);
            BeanUtils.copyProperties(ingredienteBase, ingredienteBaseAtual, "id");
            return ingredienteBaseModelAssemblerDTO.toModel(ingredienteBaseService.salvar(ingredienteBaseAtual));
        } catch (IngredienteBaseNãoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{baseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long baseId) {
        IngredienteBase ingredienteBase = ingredienteBaseService.buscarOuFalhar(baseId);
        ingredienteBaseService.excluir(baseId);
    }

}