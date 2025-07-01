package com.rosane.cafeteriaweb.api.controller;

import com.rosane.cafeteriaweb.api.assembleDTO.BebidaModelAssemblerDTO;
import com.rosane.cafeteriaweb.api.assembleDTO.BebidaModelDisassemblerDTO;
import com.rosane.cafeteriaweb.api.assembleDTO.IngredienteBaseModelAssemblerDTO;
import com.rosane.cafeteriaweb.api.assembleDTO.IngredienteBaseModelDisassemblerDTO;
import com.rosane.cafeteriaweb.api.modelDTO.BebidaModelDTO;
import com.rosane.cafeteriaweb.api.modelDTO.IngredienteBaseModelDTO;
import com.rosane.cafeteriaweb.api.modelDTO.inputDTO.BebidaInputDTO;
import com.rosane.cafeteriaweb.api.modelDTO.inputDTO.IngredienteBaseInputDTO;
import com.rosane.cafeteriaweb.domain.exception.BebidaNãoEncontradoException;
import com.rosane.cafeteriaweb.domain.exception.IngredienteBaseNãoEncontradoException;
import com.rosane.cafeteriaweb.domain.exception.NegocioException;
import com.rosane.cafeteriaweb.domain.model.Bebida;
import com.rosane.cafeteriaweb.domain.model.IngredienteBase;
import com.rosane.cafeteriaweb.domain.repository.BebidaRepository;
import com.rosane.cafeteriaweb.domain.repository.IngredienteBaseRepository;
import com.rosane.cafeteriaweb.domain.service.BebidaService;
import com.rosane.cafeteriaweb.domain.service.IngredienteBaseService;
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