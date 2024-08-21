package com.obd.agnx.response.fuel;

import com.obd.agnx.response.OBDResponse;

public class FuelTrimResponse extends OBDResponse {

    public FuelTrimResponse() {
        super("01 06");
    }

    @Override
    public String getDefaultResponse() {
        return "41 06 82"; // Default response with 0% fuel trim
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 1.6% fuel trim
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
        int hexValue = Math.round((percentage + 100) * 128 / 100);
        return "41 06" + String.format("%02X", hexValue);
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse();
    }
}