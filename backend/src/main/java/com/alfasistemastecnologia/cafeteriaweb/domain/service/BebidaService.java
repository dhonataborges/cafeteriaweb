package com.alfasistemastecnologia.cafeteriaweb.domain.service;

import com.alfasistemastecnologia.cafeteriaweb.api.assembleDTO.BebidaModelDisassemblerDTO;
import com.alfasistemastecnologia.cafeteriaweb.api.modelDTO.inputDTO.BebidaInputDTO;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.BebidaNãoEncontradoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.exception.EntidadeEmUsoException;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.Bebida;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.BebidaClassica;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.IngredienteAdicional;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.IngredienteBase;
import com.alfasistemastecnologia.cafeteriaweb.domain.repository.BebidaClassicaRepository;
import com.alfasistemastecnologia.cafeteriaweb.domain.repository.BebidaRepository;
import com.alfasistemastecnologia.cafeteriaweb.domain.repository.IngredienteAdicionalRepository;
import com.alfasistemastecnologia.cafeteriaweb.domain.repository.IngredienteBaseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Serviço responsável por gerenciar bebidas (clássicas e personalizadas).
 * Contém lógica de verificação de ingredientes, persistência e exclusão segura.
 */
@Service
public class BebidaService {

    private static final String MSG_BEBIDA_EM_USO = "Bebida do código %d não pode ser removida, pois está em uso.";

    @Autowired
    private BebidaRepository bebidaRepository;

    @Autowired
    private IngredienteBaseRepository ingredienteBaseRepository;

    @Autowired
    private IngredienteAdicionalRepository ingredienteAdicionalRepository;

    @Autowired
    private BebidaClassicaRepository bebidaClassicaRepository;

    @Autowired
    private BebidaModelDisassemblerDTO bebidaModelDisassemblerDTO;

    /**
     * Salva uma bebida no banco de dados, associando corretamente os ingredientes,
     * e verificando se ela corresponde a uma bebida clássica conhecida.
     */
    @Transactional
    public Bebida salvar(@Valid Bebida bebida) {

        // Busca os ingredientes base completos no banco usando os IDs fornecidos
        List<Long> idsBases = bebida.getIngredientesBase().stream()
                .map(IngredienteBase::getId)
                .collect(Collectors.toList());
        List<IngredienteBase> ingredientesBase = ingredienteBaseRepository.findAllById(idsBases);

        // Busca os ingredientes adicionais completos no banco usando os IDs fornecidos
        List<Long> idsAdicionais = bebida.getIngredientesAdicional().stream()
                .map(IngredienteAdicional::getId)
                .collect(Collectors.toList());
        List<IngredienteAdicional> ingredientesAdicional = ingredienteAdicionalRepository.findAllById(idsAdicionais);

        // Atualiza a bebida com os ingredientes completos (com nomes válidos do banco)
        bebida.setIngredientesBase(ingredientesBase);
        bebida.setIngredientesAdicional(ingredientesAdicional);

        // Concatena os nomes de todos os ingredientes, normalizando (sem acento, minúsculo)
        Set<String> nomesIngredientes = Stream.concat(
                ingredientesBase.stream().map(i -> normalizar(i.getNome())),
                ingredientesAdicional.stream().map(i -> normalizar(i.getNome()))
        ).collect(Collectors.toSet());

        // Verifica se os ingredientes correspondem a alguma bebida clássica
        Optional<String> nomeClassica = verificarNomeBebidaClassica(nomesIngredientes, bebida.getBebidaCriada());

        // Define o nome da bebida: se bater com uma clássica, usa o nome; senão, define como "Bebida Personalizada"
        String nomeFinal = nomeClassica.orElse("bebida personalizada");
        bebida.setBebidaCriada(nomeFinal);

        // Salva a bebida no banco
        return bebidaRepository.save(bebida);
    }

    public Bebida montarBebidaComNomeClassicoOuPersonalizado(BebidaInputDTO bebidaInputDTO) {
        Bebida bebida = bebidaModelDisassemblerDTO.toDomainObject(bebidaInputDTO);

        // Busca os ingredientes base completos no banco usando os IDs fornecidos
        List<Long> idsBases = bebida.getIngredientesBase().stream()
                .map(IngredienteBase::getId)
                .collect(Collectors.toList());
        List<IngredienteBase> ingredientesBase = ingredienteBaseRepository.findAllById(idsBases);

        // Busca os ingredientes adicionais completos no banco usando os IDs fornecidos
        List<Long> idsAdicionais = bebida.getIngredientesAdicional().stream()
                .map(IngredienteAdicional::getId)
                .collect(Collectors.toList());
        List<IngredienteAdicional> ingredientesAdicional = ingredienteAdicionalRepository.findAllById(idsAdicionais);

        // Atualiza a bebida com os ingredientes completos (com nomes válidos do banco)
        bebida.setIngredientesBase(ingredientesBase);
        bebida.setIngredientesAdicional(ingredientesAdicional);

        // Concatena os nomes de todos os ingredientes, normalizando (sem acento, minúsculo)
        Set<String> nomesIngredientes = Stream.concat(
                ingredientesBase.stream().map(i -> normalizar(i.getNome())),
                ingredientesAdicional.stream().map(i -> normalizar(i.getNome()))
        ).collect(Collectors.toSet());

        // Verifica se os ingredientes correspondem a alguma bebida clássica
        Optional<String> nomeClassica = verificarNomeBebidaClassica(nomesIngredientes, bebida.getBebidaCriada());

        // Define o nome da bebida: se bater com uma clássica, usa o nome; senão, define como "Bebida Personalizada"
        String nomeFinal = nomeClassica.orElse("bebida personalizada");
        bebida.setBebidaCriada("Você criou um(a) " + nomeFinal + "!");

        return bebida;
    }

    /**
     * Exclui uma bebida pelo ID. Lança exceções customizadas se não encontrada ou se estiver em uso.
     */
    @Transactional
    public void excluir(Long bebidaId) {
        try {
            bebidaRepository.deleteById(bebidaId);
        } catch (EmptyResultDataAccessException e) {
            // Se o ID não existir
            throw new BebidaNãoEncontradoException(bebidaId);
        } catch (DataIntegrityViolationException e) {
            // Se a bebida estiver sendo usada em outra tabela (chave estrangeira, etc)
            throw new EntidadeEmUsoException(String.format(MSG_BEBIDA_EM_USO, bebidaId));
        }
    }

    /**
     * Retorna uma bebida pelo ID ou lança exceção se não existir.
     */
    public Bebida buscarOuFalhar(Long bebidaId) {
        return bebidaRepository.findById(bebidaId)
                .orElseThrow(() -> new BebidaNãoEncontradoException(bebidaId));
    }

    /**
     * Verifica se os ingredientes informados pelo usuário correspondem
     * a alguma bebida clássica cadastrada.
     *
     * @param nomesIngredientesUsuario ingredientes fornecidos (normalizados)
     * @param nomeAtual nome atual da bebida (evita duplicidade)
     * @return Optional contendo o nome da bebida clássica correspondente ou "Bebida Personalizada"
     */
    public Optional<String> verificarNomeBebidaClassica(Set<String> nomesIngredientesUsuario, String nomeAtual) {
        List<BebidaClassica> bebidasClassicas = bebidaClassicaRepository.findAll();

        // Normaliza os ingredientes informados pelo usuário
        Set<String> normalizadosUsuario = nomesIngredientesUsuario.stream()
                .map(BebidaService::normalizar)
                .collect(Collectors.toSet());

        for (BebidaClassica classica : bebidasClassicas) {
            // Normaliza os ingredientes da bebida clássica
            Set<String> ingredientesClassica = classica.getIngredientesBase().stream()
                    .map(i -> normalizar(i.getNome()))
                    .collect(Collectors.toSet());

            // Compara os dois conjuntos de ingredientes (usuário vs clássica)
            if (normalizadosUsuario.equals(ingredientesClassica)
                    && !classica.getNome().equalsIgnoreCase(nomeAtual)) {
                return Optional.of(classica.getNome());
            }
        }

        // Se não houver correspondência, considera personalizada
        return Optional.of("Bebida Personalizada");
    }

    /**
     * Normaliza texto para comparação: remove acentos, converte para minúsculas e tira espaços.
     */
    public static String normalizar(String texto) {
        if (texto == null) return null;

        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "") // remove acentos
                .toLowerCase() // deixa minúsculo
                .trim(); // remove espaços desnecessários
    }
}