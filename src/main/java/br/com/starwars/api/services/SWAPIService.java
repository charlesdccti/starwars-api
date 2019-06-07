package br.com.starwars.api.services;

import br.com.starwars.api.config.cache.SWAPICacheConfig;
import br.com.starwars.api.domain.dto.SWAPISearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/**
 * Serviço responsável por acessar a API pública
 * do Star Wars para obter dados sobre os planetas
 * dos filmes.
 * 
 * https://swapi.co/
 * */

@Service
public class SWAPIService {

	@Value("${api.publica.starwars.url}")
	private String swapiUrl;
	
	private final RestTemplate restTemplate;
	
	@Autowired
	public SWAPIService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public SWAPISearchDTO buscarPlanetaPorNome(String nome) {
		URI uri = UriComponentsBuilder.fromHttpUrl(swapiUrl)
			.path("/planets/")
			.queryParam("search", nome)
			.build().toUri();
		
		return buscarDadosSWAPI(uri, new ParameterizedTypeReference<SWAPISearchDTO>() {});
	}
	
	@Cacheable(value = SWAPICacheConfig.PLANETAS_POR_PAGINA_SWAPI, unless = "#result == null")
	public Map<String, Object> buscarPlanetas(int pagina) {
		URI uri = UriComponentsBuilder.fromHttpUrl(swapiUrl)
			.path("/planets/")
			.queryParam("page", pagina)
			.build().toUri();
		
		return buscarDadosSWAPI(uri, new ParameterizedTypeReference<Map<String, Object>>() {});
	}

	@Cacheable(value = SWAPICacheConfig.PLANETAS_POR_ID_SWAPI, unless = "#result == null")
	public Map<String, Object> buscarPlanetaPorId(int planetaId) {
		URI uri = UriComponentsBuilder.fromHttpUrl(swapiUrl)
				.path("/planets/{planetaId}/")
				.buildAndExpand(planetaId)
				.toUri();

		return buscarDadosSWAPI(uri, new ParameterizedTypeReference<Map<String, Object>>() {});
	}

	private <T> T buscarDadosSWAPI(URI uri, ParameterizedTypeReference<T> typeReference) {
		RequestEntity<Void> request = RequestEntity.get(uri)
				.accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<T> response = restTemplate.exchange(request,typeReference);
		return response.getBody();
	}
}
