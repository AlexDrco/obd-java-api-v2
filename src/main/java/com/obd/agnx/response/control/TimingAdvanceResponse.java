package com.obd.agnx.response.control;

import com.obd.agnx.response.OBDResponse;

public class TimingAdvanceResponse extends OBDResponse {

    public TimingAdvanceResponse() {
        super("01 0E");
    }

    @Override
    public String getDefaultResponse() {
        return "41 0E 50"; // Example default response
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Timing advance does not vary
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse(); // Timing advance does not vary
    }

    @Override
    public String stringToHex(String response) {
        double advance = Double.parseDouble(response);
        int value = (int) ((advance + 64) * 2);
        return String.format("41 0E %02X", value);
    }

    @Override
    public String getNoErrorResponse(String initialValue) {
        return getDefaultResponse();
    }
}