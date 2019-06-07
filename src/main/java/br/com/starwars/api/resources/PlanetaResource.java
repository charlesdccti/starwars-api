package br.com.starwars.api.resources;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import br.com.starwars.api.domain.Planeta;
import br.com.starwars.api.domain.dto.NovoPlanetaDTO;
import br.com.starwars.api.services.PlanetaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@RestController
@RequestMapping("/api/v1/planetas")
@Api(value = "Planetas")
public class PlanetaResource {

    private final PlanetaService service;

    @Autowired
    public PlanetaResource(PlanetaService service) {
        this.service = service;
    }
    
    @GetMapping
    @ApiOperation(value = "Retorna uma lista com todos os planetas", response = Planeta[].class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Planetas retornados com sucesso.") } )
    public ResponseEntity<List<Planeta>> findAll() {
        
    	return ResponseEntity
    			.ok().body(service.findAll());
    }

    @GetMapping("/{planetaId}")
    @ApiOperation(value = "Retorna um planeta pelo ID", response = Planeta.class)
    @ApiResponses(value = { @ApiResponse(code = 404, message = "Planeta não encontrado.") } )
    public ResponseEntity<Planeta> findPlanetaById(
    		@ApiParam(value = "ID do planeta a ser buscado", required = true, example = "5cfa4d890274390026f1f635")
    		@PathVariable("planetaId") String planetaId) {
        
    	return ResponseEntity
    			.ok().body(service.findById(planetaId));
    }

    @GetMapping("/planeta/{nome}")
    @ApiOperation(value = "Retorna um planeta pelo nome", response = Planeta.class)
    @ApiResponses(value = { @ApiResponse(code = 404, message = "Planeta não encontrado.") } )
    public ResponseEntity<Planeta> findPlanetaByNome(
    		@ApiParam(value = "Nome do planeta a ser buscado", required = true, example = "Tatooine") 
    		@PathVariable("nome") String nome) {
        
    	Planeta planeta = service
    			.findByNome(UriUtils.decode(nome, StandardCharsets.UTF_8));
        return ResponseEntity.ok().body(planeta);
    }

    @PostMapping
    @ApiOperation(value = "Cadastra um novo planeta", response = Void.class, consumes = "application/json")
    @ApiResponses(value = { 
    		@ApiResponse(code = 201, message = "Planeta cadastrado com sucesso.",
    				responseHeaders = { @ResponseHeader(name = "Location", description = "Url com o ID do novo recurso criado",
    													response = String.class) } ), 
    		@ApiResponse(code = 400, message = "Não é possível cadastrar planetas com o mesmo nome."),
    		@ApiResponse(code = 422, message = "Erro de validação de campos.")})
    public ResponseEntity<Void> inserir(@Valid @RequestBody NovoPlanetaDTO planetaDTO) {
        Planeta planeta = service.converterParaPlaneta(planetaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{planetaId}")
                .buildAndExpand(service.inserir(planeta).getId())
                .toUri();
        
        return ResponseEntity
        		.created(uri).build();
    }

    @DeleteMapping("/{planetaId}")
    @ApiOperation(value = "Remove um planeta pelo ID", response = Void.class)
    @ApiResponses(value = { @ApiResponse(code = 404, message = "Planeta não encontrado.") } )
    public ResponseEntity<Void> removerPlaneta(
    		@ApiParam(value = "ID do planeta a ser removido", required = true, example = "5cfa4d890274390026f1f635")
    		@PathVariable("planetaId") String planetaId) {
        
    	service.remover(planetaId);
        return ResponseEntity.noContent().build();
    }
}
