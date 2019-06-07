package br.com.starwars.api.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.*;

import org.junit.Test;

import br.com.starwars.api.StarwarsApiApplicationTests;


public class SWAPIResourceTest extends StarwarsApiApplicationTests {

    private static final Integer ID = 5;
    private static final Integer PAGINA = 2;
    
    @Test
    public void deveRealizarUmaBuscaPaginadaSWAPIEStatusCode200() {
    	given()
    		.port(super.porta)
    		.queryParam("pagina", PAGINA)
    	.get("/swapi/planetas")
    	.then()
			.log().headers().and()
			.log().body().and()
			.assertThat()
			.statusCode(OK.value())
			.extract().body()
			.jsonPath().getMap(".");
    }
    
    @Test
    public void deveBuscarOPlanetaPeloIdSWAPIEStatusCode200() {
    	given()
			.port(super.porta)
			.pathParam("planetaId", ID)
		.get("/swapi/planetas/{planetaId}")
		.then()
			.log().headers().and()
			.log().body().and()
			.assertThat()
			.statusCode(OK.value())
			.extract().body()
			.jsonPath().getMap(".");
    }
    
    @Test
    public void deveRetornarUmErroQuandoNaoEncontrarAPaginaDoRecursoEStatusCode404() {
    	given()
			.port(super.porta)
			.queryParam("pagina", 10)
		.get("/swapi/planetas")
		.then()
			.log().headers().and()
			.log().body().and()
			.assertThat()
			.statusCode(NOT_FOUND.value())
			.body("status", equalTo(NOT_FOUND.value()), 
				  "erro", equalTo("Recurso não encontrado"),
				  "mensagem", equalTo("Recurso não encontrado na API pública do Star Wars."),
				  "caminho", equalTo("/swapi/planetas"),
				  "exception", equalTo("org.springframework.web.client.HttpClientErrorException$NotFound"));
    }

	@Test
	public void deveRetornarUmErroQuandoNaoEncontrarORecursoPeloIdEStatusCode404() {
		given()
			.port(super.porta)
			.pathParam("planetaId", 80)
		.get("/swapi/planetas/{planetaId}")
		.then()
			.log().headers().and()
			.log().body().and()
			.assertThat()
			.statusCode(NOT_FOUND.value())
			.body("status", equalTo(NOT_FOUND.value()),
					"erro", equalTo("Recurso não encontrado"),
					"mensagem", equalTo("Recurso não encontrado na API pública do Star Wars."),
					"caminho", equalTo("/swapi/planetas/80"),
					"exception", equalTo("org.springframework.web.client.HttpClientErrorException$NotFound"));
	}
}
