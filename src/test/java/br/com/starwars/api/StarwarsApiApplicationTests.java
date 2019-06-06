package br.com.starwars.api;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, 
		classes = { MongoClientTests.class, StarwarsApiApplication.class, 
					EmbeddedMongoAutoConfiguration.class }
	)
public abstract class StarwarsApiApplicationTests {

	@LocalServerPort
	protected int porta;

	@Before
	public void setUp() throws Exception {
		RestAssured.port = porta;
	}

}
