package com.fredrischter.sessioncontrol;

import com.fredrischter.sessioncontrol.resource.ChargingSessionsResource;

public class App {

	public App() throws Exception {
		new ChargingSessionsResource();
		System.out.println("Try curl -X POST 'http://localhost:4567/chargingSessions'");
	}
	
	public static void main(String []args) throws Exception {
		new App();
	}
}
