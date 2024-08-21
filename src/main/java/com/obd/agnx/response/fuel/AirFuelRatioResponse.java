package com.obd.agnx.response.fuel;

import com.obd.agnx.response.OBDResponse;

public class AirFuelRatioResponse extends OBDResponse {

    public AirFuelRatioResponse() {
        super("01 44");
    }

    @Override
    public String getDefaultResponse() {
        return "41 44 80 00"; // Default response with 14.7:1 AFR which is the ideal AFR
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0:1 AFR
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
        float afr = Float.parseFloat(response);
        int hexValue = Math.round((afr / 14.7f) * 32768);
        return "41 44" + String.format("%04X", hexValue);
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse();
    }
}