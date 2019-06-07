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
	
	public static final String PLANETAS_POR_ID = "PLANETAS_POR_ID";
	public static final String PLANETAS_POR_PAGINA = "PLANETAS_POR_PAGINA";
	
	@Bean
	@Primary
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager(PLANETAS_POR_ID, PLANETAS_POR_PAGINA);
		cacheManager.setAllowNullValues(false);
		cacheManager.setCaffeine(caffeineCacheBuilder());
		return cacheManager;
	}

	Caffeine<Object, Object> caffeineCacheBuilder() {
		return Caffeine.newBuilder().initialCapacity(1000).expireAfterWrite(15, TimeUnit.MINUTES);
	}
}
