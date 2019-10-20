package com.fredrischter.sessioncontrol.model;

public class ChargingSessionsSummary {

    private int totalCount = 0;
    private int startedCount = 0;
    private int stoppedCount = 0;

    public int getTotalCount() {
        return totalCount;
    }

    public int getStartedCount() {
        return startedCount;
    }

    public int getStoppedCount() {
        return stoppedCount;
    }

    public void addInProgress() {
        totalCount++;
        startedCount++;
    }

    public void addFinished() {
        totalCount++;
        stoppedCount++;
    }
}
