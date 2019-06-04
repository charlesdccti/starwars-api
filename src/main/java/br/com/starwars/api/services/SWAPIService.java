package br.com.starwars.api.services;

import br.com.starwars.api.domain.dto.SWAPISearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;


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
	
	public Map<String, Object> buscarPlanetas(int pagina) {
		URI uri = UriComponentsBuilder.fromHttpUrl(swapiUrl)
			.path("/planets/")
			.queryParam("page", pagina)
			.build().toUri();
		
		return buscarDadosSWAPI(uri, new ParameterizedTypeReference<Map<String, Object>>() {});
	}

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
