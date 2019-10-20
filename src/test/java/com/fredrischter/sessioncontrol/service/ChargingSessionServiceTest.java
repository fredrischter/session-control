package com.fredrischter.sessioncontrol.service;

import static org.junit.Assert.assertEquals;

import com.fredrischter.sessioncontrol.model.*;
import com.fredrischter.sessioncontrol.service.exceptions.*;
import org.junit.*;

import java.util.UUID;

public class ChargingSessionServiceTest {

	private static final UUID INEXISTENT_ID = UUID.randomUUID();
	private static final String STATION_A = "11111";
	private static final String STATION_B = "22222";
	private static final String STATION_C = "33333";
	private static final String STATION_D = "44444";

	ChargingSessionService chargingSessionService;

	@Before
	public void setup() {
		chargingSessionService = new ChargingSessionService();
	}

	@Test
	public void creatingSession() {
		// When
		createSession(STATION_A);
	}

	@Test(expected = ChargingSessionNotFoundException.class)
	public void sessionNotFoundTest() throws ChargingSessionNotFoundException {
		// When
		chargingSessionService.stopChargingSession(INEXISTENT_ID);
	}

	@Test(expected = ChargingSessionWithoutStationId.class)
	public void creatingWithoutStationId() {
		// When
		ChargingSession chargingSession = new ChargingSession();
		chargingSessionService.submitNewChargingSession(chargingSession);
	}

	@Test(expected = ChargingSessionAlreadyFinished.class)
	public void alreadyFinished() throws ChargingSessionNotFoundException, ChargingSessionAlreadyFinished {
		// Given
		UUID idA = createSession(STATION_A);
		chargingSessionService.stopChargingSession(idA);

		// When
		chargingSessionService.stopChargingSession(idA);
	}

	@Test(expected = ChargingStationIsBusy.class)
	public void busy() throws ChargingSessionNotFoundException, ChargingSessionAlreadyFinished {
		// Given
		createSession(STATION_A);

		// When
		createSession(STATION_A);
	}

	@Test
	public void summaryCheck() throws ChargingSessionNotFoundException, ChargingSessionAlreadyFinished {
		// Given
		UUID idA = createSession(STATION_A);
		createSession(STATION_B);
		createSession(STATION_C);
		createSession(STATION_D);
		chargingSessionService.stopChargingSession(idA);

		// When
		ChargingSessionsSummary chargingSessionsSummary = chargingSessionService.retrieveSummary();
		
		// Then
		assertEquals(3, chargingSessionsSummary.getStartedCount());
		assertEquals(1, chargingSessionsSummary.getStoppedCount());
		assertEquals(4, chargingSessionsSummary.getTotalCount());
	}

	private UUID createSession(String stationId) {
		ChargingSession chargingSessionA = new ChargingSession();
		chargingSessionA.setStationId(stationId);
		return chargingSessionService.submitNewChargingSession(chargingSessionA).getId();
	}
	
}
