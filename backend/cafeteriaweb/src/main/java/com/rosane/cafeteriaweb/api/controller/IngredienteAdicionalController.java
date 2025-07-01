package com.rosane.cafeteriaweb.api.controller;

import com.rosane.cafeteriaweb.api.assembleDTO.IngredienteAdicionalModelAssemblerDTO;
import com.rosane.cafeteriaweb.api.assembleDTO.IngredienteAdicionalModelDisassemblerDTO;
import com.rosane.cafeteriaweb.api.assembleDTO.IngredienteBaseModelAssemblerDTO;
import com.rosane.cafeteriaweb.api.assembleDTO.IngredienteBaseModelDisassemblerDTO;
import com.rosane.cafeteriaweb.api.modelDTO.IngredienteAdicionalModelDTO;
import com.rosane.cafeteriaweb.api.modelDTO.IngredienteBaseModelDTO;
import com.rosane.cafeteriaweb.api.modelDTO.inputDTO.IngredienteAdicionalInputDTO;
import com.rosane.cafeteriaweb.api.modelDTO.inputDTO.IngredienteBaseInputDTO;
import com.rosane.cafeteriaweb.domain.exception.IngredienteAdicionalNãoEncontradoException;
import com.rosane.cafeteriaweb.domain.exception.IngredienteBaseNãoEncontradoException;
import com.rosane.cafeteriaweb.domain.exception.NegocioException;
import com.rosane.cafeteriaweb.domain.model.IngredienteAdicional;
import com.rosane.cafeteriaweb.domain.model.IngredienteBase;
import com.rosane.cafeteriaweb.domain.repository.IngredienteAdicionalRepository;
import com.rosane.cafeteriaweb.domain.repository.IngredienteBaseRepository;
import com.rosane.cafeteriaweb.domain.service.IngredienteAdicionalService;
import com.rosane.cafeteriaweb.domain.service.IngredienteBaseService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/ingrediente-adicional")
public class IngredienteAdicionalController {

    @Autowired
    private IngredienteAdicionalService ingredienteAdicionalService;

    @Autowired
    private IngredienteAdicionalRepository ingredienteAdicionalRepository;

    @Autowired
    private IngredienteAdicionalModelAssemblerDTO ingredienteAdicionalModelAssemblerDTO;

    @Autowired
    private IngredienteAdicionalModelDisassemblerDTO ingredienteAdicionalModelDisassemblerDTO;

    @GetMapping
    public List<IngredienteAdicionalModelDTO> listar() {
        return ingredienteAdicionalModelAssemblerDTO.toCollectionModel(ingredienteAdicionalRepository.findAll());
    }

    @GetMapping("/{adicionalId}")
    public IngredienteAdicionalModelDTO buscar(@PathVariable Long adicionalId) {
        IngredienteAdicional ingredienteAdicional = ingredienteAdicionalService.buscarOuFalhar(adicionalId);
        return ingredienteAdicionalModelAssemblerDTO.toModel(ingredienteAdicional);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public IngredienteAdicionalModelDTO adicionar(@RequestBody @Valid IngredienteAdicionalInputDTO ingredienteAdicionalInputDTO) {
        try {
            IngredienteAdicional ingredienteAdicional = ingredienteAdicionalModelDisassemblerDTO.toDomainObject(ingredienteAdicionalInputDTO);
            return ingredienteAdicionalModelAssemblerDTO.toModel(ingredienteAdicionalService.salvar(ingredienteAdicional));
        } catch (IngredienteAdicionalNãoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{adicionalId}")
    public IngredienteAdicionalModelDTO atualizar(@PathVariable Long adicionalId, @RequestBody @Valid IngredienteAdicionalInputDTO ingredienteAdicionalInputDTO) {
        try {
            IngredienteAdicional ingredienteAdicional = ingredienteAdicionalModelDisassemblerDTO.toDomainObject(ingredienteAdicionalInputDTO);
            IngredienteAdicional ingredienteAdicionalAtual = ingredienteAdicionalService.buscarOuFalhar(adicionalId);
            BeanUtils.copyProperties(ingredienteAdicional, ingredienteAdicionalAtual, "id");
            return ingredienteAdicionalModelAssemblerDTO.toModel(ingredienteAdicionalService.salvar(ingredienteAdicionalAtual));
        } catch (IngredienteAdicionalNãoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{adicionalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long adicionalId) {
        IngredienteAdicional ingredienteAdicional = ingredienteAdicionalService.buscarOuFalhar(adicionalId);
        ingredienteAdicionalService.excluir(adicionalId);
    }

}