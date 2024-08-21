package com.obd.agnx.response.pressure;

import com.obd.agnx.response.OBDResponse;

public class FuelRailPressureResponse extends OBDResponse {

    public FuelRailPressureResponse() {
        super("01 23");
    }

    @Override
    public String getDefaultResponse() {
        return "41 23 00 1A"; // Default response with 261 kPa fuel rail pressure
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 0 kPa fuel rail pressure
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
        int pressureInt = Math.round(pressure / 10); // Fuel rail pressure is divided by 10 in the command
        return String.format("41 23 %04X", pressureInt);
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse();
    }
}