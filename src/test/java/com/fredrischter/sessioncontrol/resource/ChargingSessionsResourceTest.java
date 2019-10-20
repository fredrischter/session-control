package com.fredrischter.sessioncontrol.resource;

import org.junit.Ignore;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class ChargingSessionsResourceTest {

	private static final int port = 4567;
	private static final int OK = 200;
	private static final int NOT_FOUND = 404;
	private static final int BAD_REQUEST = 400;

	static {
		new ChargingSessionsResource();
	}

	@Test
	public void notFound() {
		given()
			.port(port)
		.when()
			.get("/chargingSessions/123456")
		.then()
			.statusCode(NOT_FOUND);
	}

	@Test
	@Ignore
	public void creatingWithoutId() {
		given()
			.port(port)
		.when()
			.body("{}")
			.post("/chargingSessions")
		.then()
			.statusCode(BAD_REQUEST);
	}

	@Test
	public void creating() {
		given()
			.port(port)
		.when()
			.body("{\"stationId\":\"ABC-12345\"}")
			.post("/chargingSessions")
		.then()
			.statusCode(OK);
	}

	@Test
	public void gettingList() {
		given()
			.port(port)
			.when()
			.get("/chargingSessions")
			.then()
			.statusCode(OK);
	}

	@Test
	public void gettingSummary() {
		given()
			.port(port)
			.when()
			.get("/chargingSessions/summary")
			.then()
			.statusCode(OK);
	}
	
}
