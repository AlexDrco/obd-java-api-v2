package com.obd.agnx.response.pressure;

import com.obd.agnx.response.OBDResponse;

public class BarometricPressureResponse extends OBDResponse {

    public BarometricPressureResponse() {
        super("01 33");
    }

    @Override
    public String getDefaultResponse() {
        return "41 33 65"; // Default response with 100 kPa barometric pressure
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 100 kPa barometric pressure
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
        int pressureInt = Math.round(pressure);
        return "41 33" + String.format("%02X", pressureInt);
    }

    @Override
    public String getNoErrorResponse() {
        return getDefaultResponse();
    }
}