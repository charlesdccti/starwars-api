package br.com.starwars.api.config.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class SWAPICacheConfig {
	
	public static final String PLANETAS_POR_ID_SWAPI = "PLANETAS_POR_ID";
	public static final String PLANETAS_POR_PAGINA_SWAPI = "PLANETAS_POR_PAGINA";
	public static final String NOME_PLANETA = "NOME_PLANETA";
	public static final String PLANETA_ID = "PLANETA_ID";
	
	@Bean
	@Primary
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager(PLANETAS_POR_ID_SWAPI, 
				PLANETAS_POR_PAGINA_SWAPI, NOME_PLANETA, PLANETA_ID);
		cacheManager.setAllowNullValues(false);
		cacheManager.setCaffeine(caffeineCacheBuilder());
		return cacheManager;
	}

	Caffeine<Object, Object> caffeineCacheBuilder() {
		return Caffeine.newBuilder().initialCapacity(1000).expireAfterWrite(15, TimeUnit.MINUTES);
	}
}
