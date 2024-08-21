package com.obd.agnx.response.fuel;

import com.obd.agnx.response.OBDResponse;

public class WidebandAirFuelRatioResponse extends OBDResponse {

    public WidebandAirFuelRatioResponse() {
        super("01 34");
    }

    @Override
    public String getDefaultResponse() {
        return "41 34 80 00"; // Default response with 14.7:1 AFR
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
        float wafr = Float.parseFloat(response);
        int hexValue = Math.round((wafr / 14.7f) * 32768);
        return "41 34" + String.format("%04X", hexValue);
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse();
    }
}