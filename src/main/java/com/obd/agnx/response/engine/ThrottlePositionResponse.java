package com.obd.agnx.response.engine;

import com.obd.agnx.response.OBDResponse;

public class ThrottlePositionResponse extends OBDResponse {

    public ThrottlePositionResponse() {
        super("01 11");
    }

    @Override
    public String getDefaultResponse() {
        return "41 11 03"; // Default response with 0% throttle position
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0% throttle position
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        return getDefaultResponse();
    }

    @Override
    public String stringToHex(String response) {
        float percentage = Float.parseFloat(response);
        int hexValue = Math.round(percentage * 255 / 100);
        return "41 11" + String.format("%02X", hexValue);
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse();
    }
}