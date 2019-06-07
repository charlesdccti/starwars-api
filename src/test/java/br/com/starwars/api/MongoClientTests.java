package br.com.starwars.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

@DataMongoTest
@Configuration
@AutoConfigureDataMongo
public class MongoClientTests {

	@Value("${spring.data.mongodb.host}")
	private String host;
	
	@Value("${spring.data.mongodb.port}")
	private int porta;

	@Value("${spring.data.mongodb.database}")
	private String db;

	@Bean
	public MongoClient mongoClient() {
		return new MongoClient(host, porta);
	}

	@Bean
	public MongoTemplate mongoTemplate() throws IOException {
		return new MongoTemplate(mongoClient(), db);
	}
	
}
