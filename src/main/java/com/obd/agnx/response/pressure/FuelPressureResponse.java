package com.obd.agnx.response.pressure;

import com.obd.agnx.response.OBDResponse;

public class FuelPressureResponse extends OBDResponse {

    public FuelPressureResponse() {
        super("01 0A");
    }

    @Override
    public String getDefaultResponse() {
        return "41 0A 57"; // Default response with 261 kPa fuel pressure
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 30 kPa fuel pressure
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
        float pressure;
        if (response.endsWith("psi")) {
            float psiValue = Float.parseFloat(response.replace("psi", "").trim());
            pressure = psiValue / 0.145037738F;
        } else if (response.endsWith("kPa")) {
            pressure = Float.parseFloat(response.replace("kPa", "").trim());
        } else {
            pressure = Float.parseFloat(response.trim());
        }
        int pressureInt = Math.round(pressure / 3); // Fuel pressure is multiplied by 3 in the command
        return "41 0A" + String.format("%02X", pressureInt);
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse();
    }
}