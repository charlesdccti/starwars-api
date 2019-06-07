package br.com.starwars.api.services;

import br.com.starwars.api.domain.Planeta;
import br.com.starwars.api.domain.dto.NovoPlanetaDTO;
import br.com.starwars.api.domain.dto.SWAPIPlanetaFilmesSearchDTO;
import br.com.starwars.api.domain.dto.SWAPISearchDTO;
import br.com.starwars.api.exception.PlanetaComNomeDuplicadoException;
import br.com.starwars.api.exception.PlanetaNaoEncontradoException;
import br.com.starwars.api.repository.PlanetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

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
        return repos.findByNome(StringUtils.capitalize(nomePlaneta))
                .orElseThrow(() -> new PlanetaNaoEncontradoException("Planeta "+nomePlaneta+ " não encontrado."));
    }

    @Transactional(rollbackFor = Exception.class)
    public Planeta inserir(Planeta planeta) {
        Optional<Planeta> planetaOpt = repos.findByNome(planeta.getNome());
        if (planetaOpt.isPresent()) {
            throw new PlanetaComNomeDuplicadoException("Não é possível cadastrar planetas com o mesmo nome.");
        }

        planeta.setAparicoesEmFilmes(quantidadeDeAparicoes(planeta.getNome()));
        return repos.insert(planeta);
    }

    public void remover(String planetaId) {
        findById(planetaId);
        repos.deleteById(planetaId);
    }

    public Planeta converterParaPlaneta(NovoPlanetaDTO planetaDTO) {
        return new Planeta(StringUtils.capitalize(planetaDTO.getNome()), planetaDTO.getTerreno(), planetaDTO.getClima());
    }
    
    public int quantidadeDeAparicoes(String nome) {
    	SWAPISearchDTO response = swapiService.buscarPlanetaPorNome(nome);
    	List<SWAPIPlanetaFilmesSearchDTO> results = response.getResults();
    	return results.stream()
                .filter(res -> res.getNome().equalsIgnoreCase(nome))
                .mapToInt(res -> res.getFilmes().size())
                .sum();
    }
}
