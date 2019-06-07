package br.com.starwars.api.services;

import br.com.starwars.api.StarwarsApiApplication;
import br.com.starwars.api.domain.Planeta;
import br.com.starwars.api.exception.PlanetaComNomeDuplicadoException;
import br.com.starwars.api.exception.PlanetaNaoEncontradoException;
import br.com.starwars.api.repository.PlanetaRepository;
import junit.framework.TestCase;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StarwarsApiApplication.class })
public class PlanetaServiceTest {

    @MockBean
    private PlanetaRepository repository;

    @Autowired
    private PlanetaService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Planeta planeta;

    private static final String ID = "5cf70fa4410df8002ea422rc";
    private static final String NOME = "Dagobah";
    private static final String TERRENO = "swamp, jungles";
    private static final String CLIMA = "murky";
    private static final Integer APARICOES = 3;

    @Before
    public void setUp() throws Exception {
        planeta = new Planeta(NOME, TERRENO, CLIMA);
        planeta.setId(ID);
        when(repository.findByNome(NOME)).thenReturn(Optional.empty());
        when(repository.findById(ID)).thenReturn(Optional.empty());
    }

    @Test
    public void deveSalvarUmPlanetaNoBanco() {
        when(repository.insert(planeta)).thenReturn(planeta);
        Planeta planetaTest = service.inserir(planeta);
        verify(repository).insert(planeta);

        assertThat(planetaTest.getId()).isNotEmpty();
        assertThat(planetaTest.getNome()).isEqualTo(NOME);
        assertThat(planetaTest.getTerreno()).isEqualTo(TERRENO);
        assertThat(planetaTest.getClima()).isEqualTo(CLIMA);
        assertThat(planetaTest.getAparicoesEmFilmes()).isEqualTo(APARICOES);
    }

    @Test
    public void naoDeveSalvarDoisPlanetasComOMesmoNome() throws Exception {
        when(repository.findByNome(NOME)).thenReturn(Optional.of(planeta));
        expectedException.expect(PlanetaComNomeDuplicadoException.class);
        expectedException.expectMessage("Não é possível cadastrar planetas com o mesmo nome.");
        service.inserir(planeta);
    }
    
    @Test
    public void naoDeveSalvarNoBancoSemONomeDoPlaneta() {
    	expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("O nome não pode estar vázio.");
    	service.inserir(new Planeta("", "neve", "frio"));
    }
    
    @Test
    public void naoDeveSalvarNoBancoSemOTerrenoDoPlaneta() {
    	expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("O terreno não pode estar vázio.");
    	service.inserir(new Planeta("Hoth", "", "frio"));
    }
    
    @Test
    public void naoDeveSalvarNoBancoSemOClimaDoPlaneta() {
    	expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("O clima não pode estar vázio.");
    	service.inserir(new Planeta("Hoth", "neve", ""));
    }
    
    @Test
    public void naoDeveSalvarNoBancoPassandoNullNoConstrutorDoPlaneta() {
    	expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("O construtor não deve receber informações nulas.");
    	service.inserir(new Planeta(null, "neve", "frio"));
    }

    @Test
    public void deveRemoverUmPlanetaDoBancoPeloId() {
        doNothing().when(repository).deleteById(ID);
        when(repository.findById(ID)).thenReturn(Optional.empty());
    }
    
    @Test
    public void deveLancarUmaExceptionAoTentarRemoverUmPlanetaQueNaoExiste() {
    	expectedException.expect(PlanetaNaoEncontradoException.class);
        expectedException.expectMessage("Planeta com id: "+ID+" não encontrado.");
        service.remover(ID);
    }

    @Test
    public void deveBuscarPlanetaPeloNome() {
        when(repository.findByNome(NOME)).thenReturn(Optional.of(planeta));
        Planeta planetaTest = service.findByNome(NOME);
        verify(repository).findByNome(NOME);
        
        assertThat(planetaTest.getId()).isEqualTo(ID);
        assertThat(planetaTest.getNome()).isEqualTo(NOME);
        assertThat(planetaTest.getTerreno()).isEqualTo(TERRENO);
        assertThat(planetaTest.getClima()).isEqualTo(CLIMA);
        assertThat(planetaTest.getAparicoesEmFilmes()).isNotNegative();
    }

    @Test
    public void deveBuscarOPlanetaPeloNomeELancarUmaExceptionDePlanetaNaoEncontrado() {
        expectedException.expect(PlanetaNaoEncontradoException.class);
        expectedException.expectMessage("Planeta "+NOME+" não encontrado.");
        service.findByNome(NOME);
    }

    @Test
    public void deveBuscarOPlanetaPorId() {
        when(repository.findById(ID)).thenReturn(Optional.of(planeta));
        Planeta planetaTest = service.findById(ID);
        verify(repository).findById(ID);
        
        assertThat(planetaTest.getId()).isEqualTo(ID);
        assertThat(planetaTest.getNome()).isEqualTo(NOME);
        assertThat(planetaTest.getTerreno()).isEqualTo(TERRENO);
        assertThat(planetaTest.getClima()).isEqualTo(CLIMA);
    }

    @Test
    public void deveBuscarOPlanetaPorIdELancarUmaExceptionDePlanetaNaoEncontrado() {
        expectedException.expect(PlanetaNaoEncontradoException.class);
        expectedException.expectMessage("Planeta com id: "+ID+" não encontrado.");
        service.findById(ID);
    }

    @Test
    public void deveRetornarONumeroDeAparicoesEmFilmes() {
        Integer aparicoes = service.quantidadeDeAparicoes(NOME);
        assertThat(aparicoes).isEqualTo(3);
    }

    @Test
    public void deveRetornarUmaListaComTodosOsPlanetas() {
        Planeta jakku = new Planeta("Jakku", "unknown", "unknown");
        jakku.setId("5cf70fa4410df8002ea42sw4");
        when(repository.findAll()).thenReturn(Arrays.asList(planeta, jakku));
        List<Planeta> planetasTest = service.findAll();
        MatcherAssert.assertThat(planetasTest, not(IsEmptyCollection.empty()));
    }

    @Test
    public void deveRetornarUmaListaDePlanetasVazia() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<Planeta> planetasTest = service.findAll();
        MatcherAssert.assertThat(planetasTest, IsEmptyCollection.empty());
    }
}
