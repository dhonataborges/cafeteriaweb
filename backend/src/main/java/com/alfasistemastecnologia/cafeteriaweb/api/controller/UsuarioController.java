package com.alfasistemastecnologia.cafeteriaweb.api.controller;

import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.BebidaModelAssemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.BebidaModelDisassemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.UsuarioModelAssemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.UsuarioModelDisassemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.BebidaModelDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.UsuarioModelDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.BebidaInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.UsuarioInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.BebidaNãoEncontradoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.NegocioException;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.UsuarioNãoEncontradoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.Bebida;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.Usuario;
import com.alfasistemastecnologia.cafeteriaweb.domain.repository.BebidaRepository;
import com.alfasistemastecnologia.cafeteriaweb.domain.repository.UsuarioRepository;
import com.alfasistemastecnologia.cafeteriaweb.domain.service.BebidaService;
import com.alfasistemastecnologia.cafeteriaweb.domain.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioModelAssemblerDTO usuarioModelAssemblerDTO;

    @Autowired
    private UsuarioModelDisassemblerDTO usuarioModelDisassemblerDTO;

    @GetMapping
    public List<UsuarioModelDTO> listar() {
        return usuarioModelAssemblerDTO.toCollectionModel(usuarioRepository.findAll());
    }

    @GetMapping("/{usuarioId}")
    public UsuarioModelDTO buscar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        return usuarioModelAssemblerDTO.toModel(usuario);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UsuarioModelDTO adicionar(@RequestBody @Valid UsuarioInputDTO usuarioInputDTO) {
        try {
            Usuario usuario = usuarioModelDisassemblerDTO.toDomainObject(usuarioInputDTO);
            return usuarioModelAssemblerDTO.toModel(usuarioService.salvar(usuario));
        } catch (UsuarioNãoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{usuarioId}")
    public UsuarioModelDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputDTO usuarioInputDTO) {
        try {
            Usuario usuario = usuarioModelDisassemblerDTO.toDomainObject(usuarioInputDTO);
            Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
            BeanUtils.copyProperties(usuario, usuarioAtual, "id");
            return usuarioModelAssemblerDTO.toModel(usuarioService.atualizar(usuarioId, usuarioAtual));
        } catch (UsuarioNãoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        usuarioService.excluir(usuarioId);
    }

}