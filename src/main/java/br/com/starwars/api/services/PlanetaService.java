package br.com.starwars.api.services;

import br.com.starwars.api.domain.Planeta;
import br.com.starwars.api.domain.dto.NovoPlanetaDTO;
import br.com.starwars.api.exception.PlanetaNaoEncontradoException;
import br.com.starwars.api.repository.PlanetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlanetaService {

    private final PlanetaRepository repos;

    @Autowired
    public PlanetaService(PlanetaRepository repos) {
        this.repos = repos;
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

    @Transactional
    public Planeta inserir(Planeta planeta) {
        return repos.insert(planeta);
    }

    public void remover(String planetaId) {
        findById(planetaId);
        repos.deleteById(planetaId);
    }

    public Planeta converterParaPlaneta(NovoPlanetaDTO planetaDTO) {
        return new Planeta(planetaDTO.getNome(), planetaDTO.getTerreno(), planetaDTO.getClima());
    }
}
