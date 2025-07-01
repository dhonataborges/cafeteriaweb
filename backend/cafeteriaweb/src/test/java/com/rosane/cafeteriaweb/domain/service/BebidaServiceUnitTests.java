package com.rosane.cafeteriaweb.domain.service;

import com.rosane.cafeteriaweb.domain.model.*;
import com.rosane.cafeteriaweb.domain.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de testes unitários para o serviço BebidaService.
 * Testa se o sistema é capaz de identificar corretamente uma bebida clássica com base nos ingredientes fornecidos.
 */
class BebidaServiceUnitTests {

    // Mocks dos repositórios que simulam o acesso ao banco de dados
    @Mock
    private BebidaClassicaRepository bebidaClassicaRepository;

    @Mock
    private IngredienteBaseRepository ingredienteBaseRepository;

    @Mock
    private IngredienteAdicionalRepository ingredienteAdicionalRepository;

    @Mock
    private BebidaRepository bebidaRepository;

    // Injeta os mocks no serviço que será testado
    @InjectMocks
    private BebidaService bebidaServiceTest;

    @BeforeEach
    void setup() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa se o método retorna corretamente o nome de uma bebida clássica
     * quando os ingredientes fornecidos pelo usuário forem exatamente iguais aos da bebida clássica cadastrada.
     */
    @Test
    void verificarNomeBebidaClassica_retornaNomeSeIngredientesBatem() {
        // Cria ingredientes simulados
        IngredienteBase ingrediente1 = new IngredienteBase();
        ingrediente1.setId(1L);
        ingrediente1.setNome("Expresso");

        IngredienteBase ingrediente2 = new IngredienteBase();
        ingrediente2.setId(2L);
        ingrediente2.setNome("Leite");

        // Cria bebida clássica com os ingredientes simulados
        BebidaClassica bebidaClassica1 = new BebidaClassica();
        bebidaClassica1.setNome("Latte");
        bebidaClassica1.setIngredientesBase(List.of(ingrediente1, ingrediente2));

        // Simula retorno do repositório
        when(bebidaClassicaRepository.findAll()).thenReturn(List.of(bebidaClassica1));

        // Ingredientes fornecidos pelo usuário - normalizados
        Set<String> ingredientesUsuario = new HashSet<>(Arrays.asList(
                bebidaServiceTest.normalizar("expresso"),
                bebidaServiceTest.normalizar("leite")
        ));

        // Executa o método a ser testado
        Optional<String> nomeEncontrado = bebidaServiceTest.verificarNomeBebidaClassica(ingredientesUsuario, "QualquerNome");

        // Verifica se o nome da bebida clássica foi corretamente identificado
        assertTrue(nomeEncontrado.isPresent());
        assertEquals("Latte", nomeEncontrado.get());
    }

    /**
     * Testa se o método ignora ingredientes duplicados e ainda assim reconhece a bebida clássica corretamente.
     */
    @Test
    void verificarNomeBebidaClassica_retornaNomeSeIngredientesBatemSeCasoTiverUmIngredienteIgualIgnorar() {
        // Criação de ingredientes com nomes distintos
        IngredienteBase ingrediente1 = new IngredienteBase();
        ingrediente1.setId(1L);
        ingrediente1.setNome("Expresso");

        IngredienteBase ingrediente2 = new IngredienteBase();
        ingrediente2.setId(2L);
        ingrediente2.setNome("Leite");

        IngredienteBase ingrediente3 = new IngredienteBase();
        ingrediente3.setId(3L);
        ingrediente3.setNome("Chocolate");

        // Cria bebida clássica contendo os 3 ingredientes
        BebidaClassica bebidaClassica1 = new BebidaClassica();
        bebidaClassica1.setNome("Mocha");
        bebidaClassica1.setIngredientesBase(List.of(ingrediente1, ingrediente2, ingrediente3));

        // Simula retorno do repositório
        when(bebidaClassicaRepository.findAll()).thenReturn(List.of(bebidaClassica1));

        // Ingredientes do usuário (inclui duplicação de chocolate) - normalizados
        Set<String> ingredientesUsuario = new HashSet<>(Arrays.asList(
                bebidaServiceTest.normalizar("expresso"),
                bebidaServiceTest.normalizar("leite"),
                bebidaServiceTest.normalizar("chocolate"),
                bebidaServiceTest.normalizar("chocolate")
        ));

        // Executa o método
        Optional<String> nomeEncontrado = bebidaServiceTest.verificarNomeBebidaClassica(ingredientesUsuario, "QualquerNome");

        // Verifica se o nome retornado corresponde ao esperado
        assertTrue(nomeEncontrado.isPresent());
        assertEquals("Mocha", nomeEncontrado.get());
    }

    /**
     * Testa se o método retorna "Bebida Personalizada" quando os ingredientes
     * fornecidos não correspondem a nenhuma bebida clássica cadastrada.
     */
    @Test
    void verificarNomeBebidaClassica_retornaBebidaPersonalizadaSeNaoBaterComNenhumaClassica() {
        // Cria ingredientes e bebida clássica
        IngredienteBase ing1 = new IngredienteBase();
        ing1.setNome("Expresso");

        IngredienteBase ing2 = new IngredienteBase();
        ing2.setNome("Leite");

        BebidaClassica bebida = new BebidaClassica();
        bebida.setNome("Latte");
        bebida.setIngredientesBase(List.of(ing1, ing2));

        // Simula retorno do repositório
        when(bebidaClassicaRepository.findAll()).thenReturn(List.of(bebida));

        // Ingredientes do usuário não batem com nenhuma bebida clássica - normalizados
        Set<String> ingredientesUsuario = new HashSet<>(Arrays.asList(
                bebidaServiceTest.normalizar("Expresso"),
                bebidaServiceTest.normalizar("Chantilly")
        ));

        // Executa o método
        Optional<String> nomeEncontrado = bebidaServiceTest.verificarNomeBebidaClassica(ingredientesUsuario, "QualquerNome");

        // Verifica se foi retornado "Bebida Personalizada"
        assertTrue(nomeEncontrado.isPresent());
        assertEquals("Bebida Personalizada", nomeEncontrado.get());
    }

    /**
     * Testa se o método ignora a bebida clássica se o nome atual for igual ao nome da clássica.
     */
    @Test
    void verificarNomeBebidaClassica_ignoraSeNomeAtualIgualAoNomeClassico() {
        IngredienteBase ing1 = new IngredienteBase();
        ing1.setNome("Expresso");

        IngredienteBase ing2 = new IngredienteBase();
        ing2.setNome("Leite");

        BebidaClassica bebida = new BebidaClassica();
        bebida.setNome("Latte");
        bebida.setIngredientesBase(List.of(ing1, ing2));

        when(bebidaClassicaRepository.findAll()).thenReturn(List.of(bebida));

        Set<String> ingredientesUsuario = new HashSet<>(Arrays.asList(
                bebidaServiceTest.normalizar("expresso"),
                bebidaServiceTest.normalizar("leite")
        ));

        Optional<String> resultado = bebidaServiceTest.verificarNomeBebidaClassica(ingredientesUsuario, "Latte");

        assertTrue(resultado.isPresent());
        assertEquals("Bebida Personalizada", resultado.get());
    }

}
