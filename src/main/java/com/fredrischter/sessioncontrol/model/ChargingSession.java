package com.fredrischter.sessioncontrol.model;

import com.fredrischter.sessioncontrol.service.exceptions.ChargingSessionAlreadyFinished;
import com.fredrischter.sessioncontrol.service.exceptions.ChargingSessionWithoutStationId;

import java.time.LocalDateTime;
import java.util.UUID;

public class ChargingSession {

    private UUID id;
    private String stationId;
    private LocalDateTime startedAt;
    private LocalDateTime stoppedAt;
    private StatusEnum status;

    public void start() {
        if (stationId == null) {
            throw new ChargingSessionWithoutStationId();
        }
        id = UUID.randomUUID();
        status = StatusEnum.IN_PROGRESS;
        startedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getStoppedAt() {
        return stoppedAt;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void finish() {
        if (status == StatusEnum.FINISHED) {
            throw new ChargingSessionAlreadyFinished();
        }
        status = StatusEnum.FINISHED;
        stoppedAt = LocalDateTime.now();
    }
}
