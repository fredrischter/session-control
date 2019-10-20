package com.fredrischter.sessioncontrol.repository;

import com.fredrischter.sessioncontrol.model.ChargingSession;
import java.util.*;

public class ChargingSessionRepository {

    private List<ChargingSession> chargingSessionsList = new ArrayList<ChargingSession>();
    private Map<UUID, ChargingSession> chargingSessionsMapById = new HashMap<UUID, ChargingSession>();
    private Map<String, ChargingSession> chargingSessionsByStationId = new HashMap<String, ChargingSession>();

    public ChargingSession insert(ChargingSession request) {
        request.start();
        chargingSessionsByStationId.put(request.getStationId(), request);
        chargingSessionsMapById.put(request.getId(), request);
        chargingSessionsList.add(request);
        return request;
    }

    public List<ChargingSession> list() {
        return chargingSessionsList;
    }

    public Optional<ChargingSession> findByStationId(String stationId) {
        if (chargingSessionsByStationId.containsKey(stationId)) {
            return Optional.of(chargingSessionsByStationId.get(stationId));
        }
        return Optional.empty();
    }

    public Optional<ChargingSession> findById(UUID id) {
        if (chargingSessionsMapById.containsKey(id)) {
            return Optional.of(chargingSessionsMapById.get(id));
        }
        return Optional.empty();
    }
}
