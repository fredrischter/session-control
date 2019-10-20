package com.fredrischter.sessioncontrol.resource;

import com.fredrischter.sessioncontrol.model.*;
import com.fredrischter.sessioncontrol.service.*;
import com.fredrischter.sessioncontrol.service.exceptions.*;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static spark.Spark.*;

public class ChargingSessionsResource {

    private ChargingSessionService chargingSessionService = new ChargingSessionService();
    private static final Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .create();

    public ChargingSessionsResource() {
        // Submit a new charging session for the station
        post("/chargingSessions", (req, res) -> {
            ChargingSession requestBody = GSON.fromJson(req.body(), ChargingSession.class);
            ChargingSession responseBody = submitNewChargingSession(requestBody);
            return GSON.toJson(responseBody);
        });

        // Stop charging session
        put("/chargingSessions/:id", (req, res) -> {
            UUID id = UUID.fromString(req.params(":id"));
            ChargingSession responseBody = stopChargingSession(id);
            return GSON.toJson(responseBody);
        });

        // Retrieve all charging sessions
        get("/chargingSessions", (req, res) -> GSON.toJson(retrieveAll()));

        // Retrieve a summary of submitted charging sessions
        get("/chargingSessions/summary", (req, res) -> GSON.toJson(retrieveSummary()));

        exception(ChargingSessionWithoutStationId.class, (exception, request, response) -> {
            response.status(400);
        });

        exception(ChargingSessionNotFoundException.class, (exception, request, response) -> {
            response.status(404);
        });

        exception(ChargingSessionAlreadyFinished.class, (exception, request, response) -> {
            response.status(412);
        });

        exception(ChargingStationIsBusy.class, (exception, request, response) -> {
            response.status(412);
        });

    }

    public ChargingSession stopChargingSession(UUID id) throws ChargingSessionNotFoundException, ChargingSessionAlreadyFinished {
        return chargingSessionService.stopChargingSession(id);
    }

    private ChargingSession submitNewChargingSession(ChargingSession request) {
        return chargingSessionService.submitNewChargingSession(request);
    }

    public ChargingSessionList retrieveAll() {
        return chargingSessionService.retrieveAll();
    }

    public ChargingSessionsSummary retrieveSummary() {
        return chargingSessionService.retrieveSummary();
    }

    static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime> {
        public JsonElement serialize(LocalDateTime date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }
}
