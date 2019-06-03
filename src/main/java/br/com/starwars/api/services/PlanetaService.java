package br.com.starwars.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.starwars.api.domain.Planeta;
import br.com.starwars.api.domain.dto.NovoPlanetaDTO;
import br.com.starwars.api.domain.dto.SWAPIPlanetaSearchDTO;
import br.com.starwars.api.domain.dto.SWAPISearchDTO;
import br.com.starwars.api.exception.PlanetaNaoEncontradoException;
import br.com.starwars.api.repository.PlanetaRepository;

@Service
public class PlanetaService {

    private final PlanetaRepository repos;
    private final SWAPIService swapiService;
    
    @Autowired
    public PlanetaService(PlanetaRepository repos, SWAPIService swapiService) {
        this.repos = repos;
        this.swapiService = swapiService;
    }

    public List<Planeta> findAll() {
        return repos.findAll();
    }

    public Planeta findById(String planetaId) {
        return repos.findById(planetaId)
                .orElseThrow(() -> new PlanetaNaoEncontradoException("Planeta com id: "+planetaId+" não encontrado."));
    }

    public Planeta findByNome(String nomePlaneta) {
        return repos.findByNomeIgnoreCase(nomePlaneta)
                .orElseThrow(() -> new PlanetaNaoEncontradoException("Planeta "+nomePlaneta+ " não encontrado."));
    }

    @Transactional(rollbackFor = Exception.class)
    public Planeta inserir(Planeta planeta) {
    	planeta.setQuantidadeDeAparicoes(quantidadeDeAparicoes(planeta.getNome()));
        return repos.insert(planeta);
    }

    public void remover(String planetaId) {
        findById(planetaId);
        repos.deleteById(planetaId);
    }

    public Planeta converterParaPlaneta(NovoPlanetaDTO planetaDTO) {
        return new Planeta(planetaDTO.getNome(), planetaDTO.getTerreno(), planetaDTO.getClima());
    }
    
    public int quantidadeDeAparicoes(String nome) {
    	SWAPISearchDTO response = swapiService.buscarPlanetaPorNome(nome);
    	List<SWAPIPlanetaSearchDTO> results = response.getResults();
    	
    	for (SWAPIPlanetaSearchDTO planeta : results) {
    		if (planeta.getNome().equalsIgnoreCase(nome)) {
    			return planeta.getFilmes().size();
    		}
    	}
    	
    	return 0;
    }
}
