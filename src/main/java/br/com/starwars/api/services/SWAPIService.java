package br.com.starwars.api.services;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.starwars.api.domain.dto.SWAPISearchDTO;


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
		
		RequestEntity<Void> request = RequestEntity.get(uri)
				.accept(MediaType.APPLICATION_JSON).build();
		
		ResponseEntity<SWAPISearchDTO> response = restTemplate
				.exchange(request, new ParameterizedTypeReference<SWAPISearchDTO>() {});
		
		return response.getBody();
	}
}
