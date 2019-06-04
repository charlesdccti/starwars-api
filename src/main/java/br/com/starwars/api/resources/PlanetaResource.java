package br.com.starwars.api.resources;

import br.com.starwars.api.domain.Planeta;
import br.com.starwars.api.domain.dto.NovoPlanetaDTO;
import br.com.starwars.api.services.PlanetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import javax.validation.Valid;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1/planetas")
public class PlanetaResource {

    private final PlanetaService service;

    @Autowired
    public PlanetaResource(PlanetaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Planeta>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{planetaId}")
    public ResponseEntity<Planeta> findPlanetaById(@PathVariable("planetaId") String planetaId) {
        return ResponseEntity.ok().body(service.findById(planetaId));
    }

    @GetMapping("/planeta/{nome}")
    public ResponseEntity<Planeta> findPlanetaByNome(@PathVariable("nome") String nome) {
        Planeta planeta = service.findByNome(UriUtils.decode(nome, StandardCharsets.UTF_8));
        return ResponseEntity.ok().body(planeta);
    }

    @PostMapping
    public ResponseEntity<Void> inserir(@Valid @RequestBody NovoPlanetaDTO planetaDTO) {
        Planeta planeta = service.converterParaPlaneta(planetaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{planetaId}")
                .buildAndExpand(service.inserir(planeta).getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{planetaId}")
    public ResponseEntity<Void> removerPlaneta(@PathVariable("planetaId") String planetaId) {
        service.remover(planetaId);
        return ResponseEntity.noContent().build();
    }
}
