package net.helpscout.routingds;

import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.post;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoutingdsApplicationTests {

	@Value("${local.server.port}")
	int port;

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void masterSlaveSeparationsWorks() {
	    get("/slave/customers").then().body("$.size", equalTo(2));
	    get("/master/customers").then().body("$.size", equalTo(2));

	    post("/master/customers").then().statusCode(200);

		get("/slave/customers").then().body("$.size", equalTo(2));
		get("/master/customers").then().body("$.size", equalTo(3));
	}
}
