package com.obd.agnx.response;

public abstract class OBDResponse {
    protected String command;

    public OBDResponse(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public abstract String getDefaultResponse();
    public abstract String getSimulatedDefaultResponse();
    public abstract String getSimulatedResponse(String initialValue);
}