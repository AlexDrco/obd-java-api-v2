package com.obd.agnx.response.engine;

import com.obd.agnx.response.OBDResponse;

public class LoadResponse extends OBDResponse {

    public LoadResponse() {
        super("01 04");
    }

    @Override
    public String getDefaultResponse() {
        return "41 04 0B"; // Default response with 0% load
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0% load
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
        return "41 04" + String.format("%02X", hexValue);
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse();
    }
}