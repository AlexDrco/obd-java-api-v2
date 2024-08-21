package com.obd.agnx.response.control;

import com.obd.agnx.response.OBDResponse;

public class IgnitionMonitorResponse extends OBDResponse {

    public IgnitionMonitorResponse() {
        super("AT IGN");
    }

    @Override
    public String getDefaultResponse() {
        return "ON"; // Default response indicating ignition is ON
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Ignition status does not vary
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse(); // Ignition status does not vary
    }

    @Override
    public String stringToHex(String response) {
        return response.equalsIgnoreCase("ON") ? "4F 4E" : "4F 46"; // "ON" or "OF" in hex
    }

    @Override
    public String getNoErrorResponse() {
        return "ON";
    }
}