package com.alfasistemastecnologia.cafeteriaweb.api;

import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.BebidaModelDisassemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.controller.BebidaController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.BebidaModelAssemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.BebidaModelDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.BebidaInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.Bebida;
import com.alfasistemastecnologia.cafeteriaweb.domain.service.BebidaService;
import com.alfasistemastecnologia.cafeteriaweb.domain.repository.BebidaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BebidaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BebidaService bebidaService;

    @Mock
    private BebidaRepository bebidaRepository;

    @Mock
    private BebidaModelAssemblerDTO bebidaModelAssemblerDTO;

    @Mock
    private BebidaModelDisassemblerDTO bebidaModelDisassemblerDTO;

    @InjectMocks
    private BebidaController bebidaController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bebidaController)
                .build();
    }

    // -- Teste do GET /bebida --
    @Test
    void listar_deveRetornarListaBebidas() throws Exception {
        Bebida bebida1 = new Bebida();
        bebida1.setId(1L);
        bebida1.setBebidaCriada("Latte");

        Bebida bebida2 = new Bebida();
        bebida2.setId(2L);
        bebida2.setBebidaCriada("Mocha");

        List<Bebida> bebidas = List.of(bebida1, bebida2);

        BebidaModelDTO dto1 = new BebidaModelDTO();
        dto1.setId(1L);
        dto1.setBebidaCriada("Latte");

        BebidaModelDTO dto2 = new BebidaModelDTO();
        dto2.setId(2L);
        dto2.setBebidaCriada("Mocha");

        when(bebidaRepository.findAll()).thenReturn(bebidas);
        when(bebidaModelAssemblerDTO.toCollectionModel(bebidas)).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/bebida"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].bebidaCriada").value("Latte"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].bebidaCriada").value("Mocha"));
    }

    // -- Teste do GET /bebida/{id} --
    @Test
    void buscar_deveRetornarBebidaPorId() throws Exception {
        Long bebidaId = 1L;
        Bebida bebida = new Bebida();
        bebida.setId(bebidaId);
        bebida.setBebidaCriada("Latte");

        BebidaModelDTO dto = new BebidaModelDTO();
        dto.setId(bebidaId);
        dto.setBebidaCriada("Latte");

        when(bebidaService.buscarOuFalhar(bebidaId)).thenReturn(bebida);
        when(bebidaModelAssemblerDTO.toModel(bebida)).thenReturn(dto);

        mockMvc.perform(get("/bebida/{id}", bebidaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bebidaId))
                .andExpect(jsonPath("$.bebidaCriada").value("Latte"));
    }

    // -- Teste do POST /bebida (adicionar) --
    @Test
    void adicionar_deveCriarBebida() throws Exception {
        BebidaInputDTO inputDTO = new BebidaInputDTO();
        inputDTO.setBebidaCriada("Latte");

        Bebida bebida = new Bebida();
        bebida.setId(1L);
        bebida.setBebidaCriada("Latte");

        BebidaModelDTO dto = new BebidaModelDTO();
        dto.setId(1L);
        dto.setBebidaCriada("Latte");

        when(bebidaModelDisassemblerDTO.toDomainObject(any(BebidaInputDTO.class))).thenReturn(bebida);
        when(bebidaService.salvar(any(Bebida.class))).thenReturn(bebida);
        when(bebidaModelAssemblerDTO.toModel(any(Bebida.class))).thenReturn(dto);

        mockMvc.perform(post("/bebida")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.bebidaCriada").value("Latte"));
    }

    // -- Teste do POST /bebida/verificar --
    @Test
    void verificarBebida_deveRetornarBebidaModelDTO() throws Exception {
        BebidaInputDTO inputDTO = new BebidaInputDTO();
        inputDTO.setBebidaCriada("Mocha");

        Bebida bebida = new Bebida();
        bebida.setBebidaCriada("Mocha");

        BebidaModelDTO dto = new BebidaModelDTO();
        dto.setBebidaCriada("Mocha");

        when(bebidaService.montarBebidaComNomeClassicoOuPersonalizado(any(BebidaInputDTO.class))).thenReturn(bebida);
        when(bebidaModelAssemblerDTO.toModel(any(Bebida.class))).thenReturn(dto);

        mockMvc.perform(post("/bebida/verificar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bebidaCriada").value("Mocha"));
    }

    // -- Teste do PUT /bebida/{id} (atualizar) --
    @Test
    void atualizar_deveAtualizarBebida() throws Exception {
        Long bebidaId = 1L;
        BebidaInputDTO inputDTO = new BebidaInputDTO();
        inputDTO.setBebidaCriada("Capuccino");

        Bebida bebidaAtual = new Bebida();
        bebidaAtual.setId(bebidaId);
        bebidaAtual.setBebidaCriada("Latte");

        Bebida bebidaAtualizada = new Bebida();
        bebidaAtualizada.setId(bebidaId);
        bebidaAtualizada.setBebidaCriada("Capuccino");

        BebidaModelDTO dto = new BebidaModelDTO();
        dto.setId(bebidaId);
        dto.setBebidaCriada("Capuccino");

        when(bebidaModelDisassemblerDTO.toDomainObject(any(BebidaInputDTO.class))).thenReturn(bebidaAtualizada);
        when(bebidaService.buscarOuFalhar(bebidaId)).thenReturn(bebidaAtual);
        when(bebidaService.salvar(any(Bebida.class))).thenReturn(bebidaAtualizada);
        when(bebidaModelAssemblerDTO.toModel(any(Bebida.class))).thenReturn(dto);

        mockMvc.perform(put("/bebida/{id}", bebidaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bebidaId))
                .andExpect(jsonPath("$.bebidaCriada").value("Capuccino"));
    }

    // -- Teste do DELETE /bebida/{id} --
    @Test
    void remover_deveRemoverBebida() throws Exception {
        Long bebidaId = 1L;

        Bebida bebida = new Bebida();
        bebida.setId(bebidaId);

        when(bebidaService.buscarOuFalhar(bebidaId)).thenReturn(bebida);
        doNothing().when(bebidaService).excluir(bebidaId);

        mockMvc.perform(delete("/bebida/{id}", bebidaId))
                .andExpect(status().isNoContent());

        verify(bebidaService, times(1)).excluir(bebidaId);
    }

}