package br.com.starwars.api.resources;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.starwars.api.services.SWAPIService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/swapi")
@Api(value = "SWAPI")
public class SWAPIResource {
	
	private final SWAPIService swapiService;
	
	
	public SWAPIResource(SWAPIService swapiService) {
		this.swapiService = swapiService;
	}

	@GetMapping("/planetas")
	@ApiOperation(value = "Retorna os planetas da página pesquisada", response = Map.class)
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "Planetas retornados com sucesso."),
    		@ApiResponse(code = 404, message = "Recurso não encontrado na API pública do Star Wars.") })
    public ResponseEntity<Map<String, Object>> buscarPlanetasSWAPI(
    		@ApiParam(value = "Número da página a ser buscada na API pública do Star Wars.", 
    				  name = "pagina", example = "?pagina", required = true)
    		@RequestParam(name="pagina", defaultValue="1") Integer pagina) {
		
    	return ResponseEntity
    			.ok().body(swapiService.buscarPlanetas(pagina));
    }

	@ApiOperation(value = "Retorna o planetas do Id informado", response = Map.class)
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "Planeta retornado com sucesso."),
    		@ApiResponse(code = 404, message = "Recurso não encontrado na API pública do Star Wars.")})
	@GetMapping("/planetas/{planetaId}")
	public ResponseEntity<Map<String, Object>> buscarPlanetasPorIdSWAPI(
			@ApiParam(value = "ID do planeta a ser buscado na API pública do Star Wars.",
					 name = "pagina", example = "?pagina", required = true) 
			@PathVariable("planetaId") Integer planetaId) {
		
		return ResponseEntity
				.ok().body(swapiService.buscarPlanetaPorId(planetaId));
	}
}
