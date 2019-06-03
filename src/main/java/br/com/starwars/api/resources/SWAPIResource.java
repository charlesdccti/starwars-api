package br.com.starwars.api.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.starwars.api.domain.dto.SWAPISearchDTO;
import br.com.starwars.api.services.SWAPIService;

@RestController
@RequestMapping("/swapi")
public class SWAPIResource {
	
	private final SWAPIService swapiService;
	
	
	public SWAPIResource(SWAPIService swapiService) {
		this.swapiService = swapiService;
	}

	@GetMapping("/planetas")
    public ResponseEntity<SWAPISearchDTO> buscarPlanetasSWAPI(@RequestParam(name="pagina", defaultValue="1") Integer pagina) {
    	return ResponseEntity.ok().body(swapiService.buscarPlanetas(pagina));
    }
}
