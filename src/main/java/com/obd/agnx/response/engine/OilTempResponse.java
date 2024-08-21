package com.obd.agnx.response.engine;

import com.obd.agnx.response.OBDResponse;

public class OilTempResponse extends OBDResponse {

    public OilTempResponse() {
        super("01 5C");
    }

    @Override
    public String getDefaultResponse() {
        return "41 5C 6F"; // Default response with 71°C oil temperature
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0°C oil temperature
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
        float temperature = Float.parseFloat(response);
        int hexValue = Math.round(temperature + 40); // OBD-II standard for temperature
        return "41 5C" + String.format("%02X", hexValue);
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse();
    }
}