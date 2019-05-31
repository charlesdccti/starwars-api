package br.com.starwars.api.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.starwars.api.domain.Planeta;

@Repository
public interface PlanetaRepository extends MongoRepository<Planeta, String> {

	@Transactional(readOnly = true)
	Optional<Planeta> findByName(String nome);
}
