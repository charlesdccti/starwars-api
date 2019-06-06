package br.com.starwars.api.resources;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.util.Arrays;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.starwars.api.StarwarsApiApplicationTests;
import br.com.starwars.api.domain.Planeta;
import br.com.starwars.api.domain.dto.NovoPlanetaDTO;
import br.com.starwars.api.repository.PlanetaRepository;
import br.com.starwars.api.services.PlanetaService;
import io.restassured.http.ContentType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlanetaResourceTest extends StarwarsApiApplicationTests {

	private Planeta planeta;
	
	@Autowired
	private PlanetaRepository repository;
	
	@Autowired
	private PlanetaService service;
	
    public void setUp() {
    	repository.deleteAll();
    	
		NovoPlanetaDTO alderaan = new NovoPlanetaDTO();
		alderaan.setNome("Alderaan");
		alderaan.setClima("temperate");
		alderaan.setTerreno("grasslands, mountains");
		
		NovoPlanetaDTO coruscant = new NovoPlanetaDTO();
		coruscant.setNome("Coruscant");
		coruscant.setClima("temperate");
		coruscant.setTerreno("cityscape, mountains");
		
		List<NovoPlanetaDTO> planetas = Arrays.asList(alderaan, coruscant);
		
		planetas.forEach(dto -> { 			
			planeta = service.converterParaPlaneta(dto);
			planeta = service.inserir(planeta);
		});
    }
	
	@Test
	public void deveAdicionarUmNovoPlanetaNoBancoEStatusCode201() {
		NovoPlanetaDTO planetaDto = new NovoPlanetaDTO();
		planetaDto.setNome("tatooine");
		planetaDto.setClima("arid");
		planetaDto.setTerreno("dessert");
		
		given()
			.port(super.porta)
			.request()
			.header("Accept", ContentType.JSON)
            .header("Content-type", ContentType.JSON)
            .body(planetaDto)
        .when()
        .post("/api/v1/planetas")
        .then()
	        .log().headers().and()
	        .log().body().and()
	        .assertThat()
            .statusCode(CREATED.value())
            .header("Location", is(notNullValue()));
	}
	
			
	@Test
	public void deveBuscarUmPlanetaPeloIdEStatusCode200() {		
		given()
			.port(super.porta)
			.pathParam("planetaId", planeta.getId())
		.get("/api/v1/planetas/{planetaId}")
		.then()
			.log().headers().and()
			.log().body().and()
			.assertThat()
			.statusCode(OK.value())
			.body("id", equalTo(planeta.getId()), 
				  "nome", equalTo(planeta.getNome()),
				  "clima", equalTo(planeta.getClima()),
				  "terreno", equalTo(planeta.getTerreno()),
				  "aparicoesEmFilmes", equalTo(planeta.getAparicoesEmFilmes()));
	}
	
	
	@Test
	public void deveBuscarUmPlanetaPeloNomeEStatusCode200() {
		given()
			.port(super.porta)
			.pathParam("nome", planeta.getNome())
		.get("/api/v1/planetas/planeta/{nome}")
		.then()
			.log().headers().and()
			.log().body().and()
			.assertThat()
			.statusCode(OK.value())
			.body("id", equalTo(planeta.getId()), 
				  "nome", equalTo(planeta.getNome()),
				  "clima", equalTo(planeta.getClima()),
				  "terreno", equalTo(planeta.getTerreno()),
				  "aparicoesEmFilmes", equalTo(planeta.getAparicoesEmFilmes()));
	}
	
	@Test
	public void deveRetornarTodosOsPlanetaEStatusCode200() {		
		given()
			.port(super.porta)
		.get("/api/v1/planetas")
		.then()
			.log().headers().and()
			.log().body().and()
			.assertThat()
			.statusCode(OK.value())
			.extract().body()
			.jsonPath().getList(".", Planeta.class);
	}
	
	
	@Test
	public void deveRemoverUmPlanetaPeloIdEStatusCode204() {
		given()
			.port(super.porta)
			.pathParam("planetaId", planeta.getId())
		.delete("/api/v1/planetas/{planetaId}")
		.then()
			.log().headers().and()
			.log().body().and()
			.assertThat()
			.statusCode(NO_CONTENT.value());
	}
	
	@Test
	public void deveRetornarErroQuandoTentarInserirUmPlanetaComNomeDuplicadoEStatusCode400() {
		NovoPlanetaDTO planetaDto = new NovoPlanetaDTO();
		planetaDto.setNome("Alderaan");
		planetaDto.setClima("temperate");
		planetaDto.setTerreno("cityscape, mountains");
		
		given()
			.port(super.porta)
			.request()
			.header("Accept", ContentType.JSON)
            .header("Content-type", ContentType.JSON)
            .body(planetaDto)
        .when()
        .post("/api/v1/planetas")
        .then()
	        .log().headers().and()
	        .log().body().and()
			.assertThat()
			.statusCode(BAD_REQUEST.value())
			.body("status", equalTo(BAD_REQUEST.value()), 
				  "erro", equalTo("Integridade de dados"),
				  "mensagem", equalTo("Não é possível cadastrar planetas com o mesmo nome."),
				  "caminho", equalTo("/api/v1/planetas"),
				  "exception", equalTo("br.com.starwars.api.exception.PlanetaComNomeDuplicadoException"));
	}
	
	@Test
	public void deveRetornarErroQuandoTentarInserirUmPlanetaSemNomeEStatusCode422() {
		NovoPlanetaDTO planetaDto = new NovoPlanetaDTO();
		planetaDto.setNome("");
		planetaDto.setClima("temperate");
		planetaDto.setTerreno("cityscape, mountains");
		
		given()
			.port(super.porta)
			.request()
			.header("Accept", ContentType.JSON)
            .header("Content-type", ContentType.JSON)
            .body(planetaDto)
        .when()
        .post("/api/v1/planetas")
        .then()
	        .log().headers().and()
	        .log().body().and()
			.assertThat()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("status", equalTo(UNPROCESSABLE_ENTITY.value()), 
				  "erro", equalTo("Erro de validação"),
				  "mensagem", equalTo("Erro de validação de campos"),
				  "caminho", equalTo("/api/v1/planetas"),
				  "exception", equalTo("org.springframework.web.bind.MethodArgumentNotValidException"),
				  "erros.mensagem", hasItem(equalTo("Nome do planeta é obrigatório")));
	}
	
	@Test
	public void deveRetornarErroDeValidacaoQuandoTentarInserirUmPlanetaSemClimaEStatusCode422() {
		NovoPlanetaDTO planetaDto = new NovoPlanetaDTO();
		planetaDto.setNome("Alderaan");
		planetaDto.setClima("");
		planetaDto.setTerreno("cityscape, mountains");
		
		given()
			.port(super.porta)
			.request()
			.header("Accept", ContentType.JSON)
            .header("Content-type", ContentType.JSON)
            .body(planetaDto)
        .when()
        .post("/api/v1/planetas")
        .then()
	        .log().headers().and()
	        .log().body().and()
			.assertThat()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("status", equalTo(UNPROCESSABLE_ENTITY.value()), 
				  "erro", equalTo("Erro de validação"),
				  "mensagem", equalTo("Erro de validação de campos"),
				  "caminho", equalTo("/api/v1/planetas"),
				  "exception", equalTo("org.springframework.web.bind.MethodArgumentNotValidException"),
				  "erros.mensagem", hasItem(equalTo("Clima do planeta é obrigatório")));
	}
	
	@Test
	public void deveRetornarErroDeValidacaoQuandoTentarInserirUmPlanetaSemTerrenoEStatusCode422() {
		NovoPlanetaDTO planetaDto = new NovoPlanetaDTO();
		planetaDto.setNome("Alderaan");
		planetaDto.setClima("temperate");
		planetaDto.setTerreno("");
		
		given()
			.port(super.porta)
			.request()
			.header("Accept", ContentType.JSON)
            .header("Content-type", ContentType.JSON)
            .body(planetaDto)
        .when()
        .post("/api/v1/planetas")
        .then()
	        .log().headers().and()
	        .log().body().and()
			.assertThat()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("status", equalTo(UNPROCESSABLE_ENTITY.value()), 
				  "erro", equalTo("Erro de validação"),
				  "mensagem", equalTo("Erro de validação de campos"),
				  "caminho", equalTo("/api/v1/planetas"),
				  "exception", equalTo("org.springframework.web.bind.MethodArgumentNotValidException"),
				  "erros.mensagem", hasItem(equalTo("Terreno do planeta é obrigatório")));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void deveRetornarErroDeValidacaoQuandoTentarInserirUmPlanetaSemInformacoesEStatusCode422() {
		given()
			.port(super.porta)
			.request()
			.header("Accept", ContentType.JSON)
            .header("Content-type", ContentType.JSON)
            .body(new NovoPlanetaDTO())
        .when()
        .post("/api/v1/planetas")
        .then()
	        .log().headers().and()
	        .log().body().and()
			.assertThat()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("status", equalTo(UNPROCESSABLE_ENTITY.value()), 
				  "erro", equalTo("Erro de validação"),
				  "mensagem", equalTo("Erro de validação de campos"),
				  "caminho", equalTo("/api/v1/planetas"),
				  "exception", equalTo("org.springframework.web.bind.MethodArgumentNotValidException"),
				  "erros.mensagem", hasItems(equalTo("Nome do planeta é obrigatório"),
								  			 equalTo("Clima do planeta é obrigatório"),
								  			 equalTo("Terreno do planeta é obrigatório")));
	}
	
	@Test
	public void deveRetornarErroQuandoBuscaUmPlanetaInexistentePorIdEStatusCode404() {
		given()
			.port(super.porta)
			.pathParam("planetaId", "5cf955531379ad3dfcab62be")
		.get("/api/v1/planetas/{planetaId}")
		.then()
			.log().headers().and()
			.log().body().and()
			.assertThat()
			.statusCode(NOT_FOUND.value())
			.body("status", equalTo(NOT_FOUND.value()), 
				  "erro", equalTo("Planeta não encontrado"),
				  "mensagem", equalTo("Planeta com id: 5cf955531379ad3dfcab62be não encontrado."),
				  "caminho", equalTo("/api/v1/planetas/5cf955531379ad3dfcab62be"),
				  "exception", equalTo("br.com.starwars.api.exception.PlanetaNaoEncontradoException"));
	}
	
	@Test
	public void deveRetornarErroQuandoBuscaUmPlanetaInexistentePorNomeEStatusCode404() {
		given()
			.port(super.porta)
			.pathParam("nome", "Naboo")
		.get("/api/v1/planetas/planeta/{nome}")
		.then()
			.log().headers().and()
			.log().body().and()
			.assertThat()
			.statusCode(NOT_FOUND.value())
			.body("status", equalTo(NOT_FOUND.value()), 
				  "erro", equalTo("Planeta não encontrado"),
				  "mensagem", equalTo("Planeta Naboo não encontrado."),
				  "caminho", equalTo("/api/v1/planetas/planeta/Naboo"),
				  "exception", equalTo("br.com.starwars.api.exception.PlanetaNaoEncontradoException"));
	}
	
	@Test
	public void deveRetornarErroQuandoTantarRemoverUmPlanetaInexistentePeloIdEStatusCode404() {
		given()
			.port(super.porta)
			.pathParam("planetaId", "5cf955531379ad3dfcab62be")
		.delete("/api/v1/planetas/{planetaId}")
		.then()
			.log().headers().and()
			.log().body().and()
			.assertThat()
			.statusCode(NOT_FOUND.value())
			.body("status", equalTo(NOT_FOUND.value()), 
				  "erro", equalTo("Planeta não encontrado"),
				  "mensagem", equalTo("Planeta com id: 5cf955531379ad3dfcab62be não encontrado."),
				  "caminho", equalTo("/api/v1/planetas/5cf955531379ad3dfcab62be"),
				  "exception", equalTo("br.com.starwars.api.exception.PlanetaNaoEncontradoException"));
	}
	
}
