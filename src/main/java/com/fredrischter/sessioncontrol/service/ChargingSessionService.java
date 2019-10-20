package com.fredrischter.sessioncontrol.service;

import com.fredrischter.sessioncontrol.model.ChargingSession;
import com.fredrischter.sessioncontrol.model.ChargingSessionList;
import com.fredrischter.sessioncontrol.model.ChargingSessionsSummary;
import com.fredrischter.sessioncontrol.model.StatusEnum;
import com.fredrischter.sessioncontrol.repository.ChargingSessionRepository;
import com.fredrischter.sessioncontrol.service.exceptions.ChargingSessionAlreadyFinished;
import com.fredrischter.sessioncontrol.service.exceptions.ChargingSessionNotFoundException;
import com.fredrischter.sessioncontrol.service.exceptions.ChargingStationIsBusy;

import java.util.Optional;
import java.util.UUID;

public class ChargingSessionService {

    ChargingSessionRepository chargingSessionRepository = new ChargingSessionRepository();

    public ChargingSession stopChargingSession(UUID id) throws ChargingSessionNotFoundException, ChargingSessionAlreadyFinished {
        Optional<ChargingSession> retrieved = chargingSessionRepository.findById(id);
        if (!retrieved.isPresent()) {
            throw new ChargingSessionNotFoundException();
        }
        retrieved.get().finish();
        return retrieved.get();
    }

    public ChargingSession submitNewChargingSession(ChargingSession request) throws ChargingStationIsBusy {
        Optional<ChargingSession> chargingSessionOnThisStation = chargingSessionRepository.findByStationId(request.getStationId());
        if (chargingSessionOnThisStation.isPresent() && chargingSessionOnThisStation.get().getStatus() == StatusEnum.IN_PROGRESS) {
            throw new ChargingStationIsBusy();
        }

        return chargingSessionRepository.insert(request);
    }

    public ChargingSessionList retrieveAll() {
        ChargingSessionList chargingSessionList = new ChargingSessionList();
        chargingSessionList.setChargingSessions(chargingSessionRepository.list());
        return chargingSessionList;
    }

    public ChargingSessionsSummary retrieveSummary() {
        ChargingSessionsSummary chargingSessionsSummary = new ChargingSessionsSummary();

        chargingSessionRepository.list().forEach(chargingSession -> {
            switch (chargingSession.getStatus()) {
                case IN_PROGRESS:
                    chargingSessionsSummary.addInProgress();
                break;
                case FINISHED:
                    chargingSessionsSummary.addFinished();
                break;
            }
        });

        return chargingSessionsSummary;
    }
}
