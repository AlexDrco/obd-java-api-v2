package com.obd.agnx.response.fuel;

import com.obd.agnx.response.OBDResponse;

public class ConsumptionRateResponse extends OBDResponse {

    public ConsumptionRateResponse() {
        super("01 5E");
    }

    @Override
    public String getDefaultResponse() {
        return "41 5E 00 AE"; // Default response with 8.7 L/h consumption rate for idle
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0 L/h consumption rate
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
        float rate = Float.parseFloat(response);
        int hexValue = Math.round(rate / 0.05f);
        return "41 5E" + String.format("%04X", hexValue);
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse();
    }
}