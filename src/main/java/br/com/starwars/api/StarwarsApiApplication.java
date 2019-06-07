package br.com.starwars.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import br.com.starwars.api.config.RestTemplateBean;

@EnableCaching
@SpringBootApplication(exclude=EmbeddedMongoAutoConfiguration.class)
public class StarwarsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarwarsApiApplication.class, args);
	}
	
    @Bean
	public RestTemplateBean customRestTemplate() {
		return new RestTemplateBean();
	}

	@Bean
	@DependsOn(value = { "customRestTemplate" })
	public RestTemplateBuilder restTemplateBuilder() {
		return new RestTemplateBuilder(customRestTemplate());
	}
}
