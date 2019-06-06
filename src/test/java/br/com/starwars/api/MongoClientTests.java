package br.com.starwars.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;

@DataMongoTest
@Configuration
public class MongoClientTests {

	@Value("${spring.data.mongodb.host}")
	private String host;
	
	@Value("${spring.data.mongodb.port}")
	private int porta;

	@Bean
	public MongoClient mongoClient() {
		return new MongoClient(host, porta);
	}
	
}
