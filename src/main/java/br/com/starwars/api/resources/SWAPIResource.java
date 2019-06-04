package br.com.starwars.api.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.starwars.api.domain.dto.SWAPISearchDTO;
import br.com.starwars.api.services.SWAPIService;

import java.util.Map;

@RestController
@RequestMapping("/swapi")
public class SWAPIResource {
	
	private final SWAPIService swapiService;
	
	
	public SWAPIResource(SWAPIService swapiService) {
		this.swapiService = swapiService;
	}

	@GetMapping("/planetas")
    public ResponseEntity<Map<String, Object>> buscarPlanetasSWAPI(
    		@RequestParam(name="pagina", defaultValue="1") Integer pagina) {
    	return ResponseEntity.ok().body(swapiService.buscarPlanetas(pagina));
    }

	@GetMapping("/planetas/{planetaId}")
	public ResponseEntity<Map<String, Object>> buscarPlanetasPorIdSWAPI(@PathVariable("planetaId") Integer planetaId) {
		return ResponseEntity.ok().body(swapiService.buscarPlanetaPorId(planetaId));
	}
}
