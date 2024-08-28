package com.obd.agnx.response.pressure;

import com.obd.agnx.response.OBDResponse;
import com.obd.agnx.utils.PerlinNoise;

public class IntakeManifoldPressureResponse extends OBDResponse {

    public IntakeManifoldPressureResponse() {
        super("01 0B");
    }

    @Override
    public String getDefaultResponse() {
        return "41 0B 1E"; // Default response with 30 kPa intake manifold pressure
    }

    @Override
    public String getSimulatedDefaultResponse() {
        return getDefaultResponse(); // Simulated response with 30 kPa intake manifold pressure
    }

    @Override
    public String getSimulatedResponse() {
        return getDefaultResponse();
    }

    @Override
    public String getSimulatedResponse(String initialValue) {
        int initialDecimalValue = Integer.parseInt(initialValue);
        int noisyDecimal = PerlinNoise.addNoiseToInt(initialDecimalValue, 1);
        return Integer.toString(noisyDecimal);
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
        return "41 0B" + String.format("%02X", pressureInt);
    }

    @Override
    public String getNoErrorResponse(String initialValue) {
        try {
            String numericInitialValue = initialValue.replaceAll("\\D", "");
            String response = getSimulatedResponse(numericInitialValue);
            return stringToHex(response);
        } catch (Exception e) {
            return "NODATA";
        }
    }
}