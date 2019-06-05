package br.com.starwars.api.services;

import br.com.starwars.api.domain.Planeta;
import br.com.starwars.api.exception.PlanetaComNomeDuplicadoException;
import br.com.starwars.api.exception.PlanetaNaoEncontradoException;
import br.com.starwars.api.repository.PlanetaRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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


    @Before
    public void setUp() throws Exception {
        planeta = new Planeta(NOME, TERRENO, CLIMA);
        planeta.setId(ID);
        when(repository.findByNome(NOME)).thenReturn(Optional.empty());
        when(repository.findById(ID)).thenReturn(Optional.empty());
    }

    @Test
    public void deveSalvarUmPlanetaNoBanco() {
        service.inserir(planeta);
        verify(repository).insert(planeta);
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
}
